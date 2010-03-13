#!/usr/bin/env python

"""
The 0-1 knapsack problem resolved using the Simulated Annealing algorithm
"""

import math
import operator
import pprint
import random
import sys

COOLING_STEPS = 1000
TEMP_ALPHA = 0.8

random.seed()

def generate_items(n_items=100):
    "Generate a list of items that could be stealed"
    items = []
    for n in range(0, n_items):
        # use a uniform distribution both for values and for weights
        cost = random.randint(1, 100)
        weight = random.uniform(1, 20)
        items.append((cost, weight))
    return items

def main(args):
    items = generate_items()
    pprint.pprint(items)

    start_sol = generate_random_solution(items, max_weight=40)
    print "Random solution: %s" % start_sol
    print "value: (cost: %d, weight: %f)" % compute_cost(start_sol, items)

    solution = simulated_annealing(start_sol, items, max_weight=40)
    print "Final solution: %s" % solution
    print "value: (cost: %d, weight: %f)" % compute_cost(solution, items)

    return False

def generate_random_solution(items, max_weight):
    "Generate a starting random solution"

    # generate a random solution by adding a random item
    # until we don't get over the weight
    solution = []
    while compute_cost(solution, items)[1] <= max_weight:
        idx = random.randint(0, len(items) - 1)
        # skip duplicates
        if idx not in solution:
            solution.append(idx)
    # last item makes us get over the weight so simply remove it
    # we'll look for better results after
    solution = solution[:-1]
    return solution

def simulated_annealing(solution, items, max_weight):
    "Apply the simulated annealing for solving the knapsack problem"
    best = solution
    best_value = compute_cost(solution, items)[0]
    current_sol = solution
    temperature = 1.0

    while True:
        current_value = compute_cost(best, items)[0]

        for i in range(0, COOLING_STEPS):
            moves = generate_moves(current_sol, items, max_weight)
            idx = random.randint(0, len(moves) - 1)
            random_move = moves[idx]

            delta = compute_cost(random_move, items)[0] - \
                    compute_cost(best, items)[0]

            if delta > 0:
                best = random_move
                best_value = compute_cost(best, items)[0]
                current_sol = random_move
            else:
                if math.exp(delta / float(temperature)) > random.random():
                    current_sol = random_move

        temperature = TEMP_ALPHA * temperature
        if current_value >= best_value or temperature <= 0:
            break
    return best

def generate_moves(solution, items, max_weight):
    """
    Generate all the ammissible moves starting from the input
    solution
    """
    moves = []
    # try to add another item and save as a possible move
    for idx, item in enumerate(items):
        if idx not in solution:
            move = solution[::]
            move.append(idx)

            if compute_cost(move, items)[1] <= max_weight:
                moves.append(move)

    # try to remove one item
    for idx, item in enumerate(solution):
        move = solution[::]
        del move[idx]
        if move not in moves:
            moves.append(move)

    return moves

def compute_cost(solution, items):
    """
    Return a tuple in the format (id_item1, id_item2, ...)
    for the input solution
    """
    cost, weight = 0, 0
    for item in solution:
        cost += items[item][0]
        weight += items[item][1]
    return (cost, weight)

if __name__ == '__main__':
    sys.exit(main(sys.argv))
