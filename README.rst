ADS: Algorithms and Data Structures
===================================

This repository contains a set of self-contained implementations of several
common algorithms and data structures in different programming languages.
The implementation of these algorithms/data structures is not to be intended
as a reference and most of these implementations are far from being generic
and written well enough to consider their inclusion in any production system.
Also, the list of both algorithms and data structures here is not meant to
be complete, this is mostly a list of things I already implemented and items
I have on my todo list.

Additionally, there are also some interview questions and solutions for each
programming language. The description of the interview question is in the
file itself.

Note: some of the Java implementations require Java 8

+----------------+---------------------+-----+-----+------+-----+--------+------+
|                |                     |  C  | C++ | Java |  Go | Python | Ruby |
+================+=====================+=====+=====+======+=====+========+======+
| *Caches*       | LRU Cache           |     |     |   ✓  |     |        |      |
+----------------+---------------------+-----+-----+------+-----+--------+------+
|                | Open addressing     |     |     |   ✓  |     |        |      |
|                +---------------------+-----+-----+------+-----+--------+------+
| *Dictionaries* | Chained lists       |     |     |   ✓  |     |        |      |
|                +---------------------+-----+-----+------+-----+--------+------+
|                | Binary Search Tree  |     |     |   ✓  |  ✓  |   ✓    |  ✓   |
+----------------+---------------------+-----+-----+------+-----+--------+------+
|                | Singly Linked List  |     |     |   ✓  |  ✓  |        |  ✓   |
|                +---------------------+-----+-----+------+-----+--------+------+
|                | Doubly Linked List  |     |     |   ✓  |     |        |      |
| *Lists*        +---------------------+-----+-----+------+-----+--------+------+
|                | Array List          |     |     |      |  ✓  |        |      |
|                +---------------------+-----+-----+------+-----+--------+------+
|                | Skip List           |  ✓  |     |      |     |        |      |
+----------------+---------------------+-----+-----+------+-----+--------+------+
|                | Sparse Vector       |     |     |   ✓  |     |        |  ✓   |
| *Math*         +---------------------+-----+-----+------+-----+--------+------+
|                | 90° Matrix Rotation |     |     |   ✓  |     |        |      |
+----------------+---------------------+-----+-----+------+-----+--------+------+
| *Optimization* | Simulated Annealing |     |     |      |     |   ✓    |      |
+----------------+---------------------+-----+-----+------+-----+--------+------+
| *Sets*         | Union Find          |     |     |   ✓  |     |        |  ✓   |
+----------------+---------------------+-----+-----+------+-----+--------+------+
|                | Bubble Sort         |  ✓  |     |   ✓  |  ✓  |   ✓    |  ✓   |
|                +---------------------+-----+-----+------+-----+--------+------+
|                | Selection Sort      |  ✓  |     |   ✓  |  ✓  |   ✓    |  ✓   |
|  *Sorting      +---------------------+-----+-----+------+-----+--------+------+
|  and           | Insertion Sort      |  ✓  |     |   ✓  |  ✓  |   ✓    |  ✓   |
|  Search*       +---------------------+-----+-----+------+-----+--------+------+
|                | Merge Sort          |     |  ✓  |   ✓  |     |   ✓    |  ✓   |
|                +---------------------+-----+-----+------+-----+--------+------+
|                | Quick Sort          |     |  ✓  |   ✓  |     |   ✓    |  ✓   |
|                +---------------------+-----+-----+------+-----+--------+------+
|                | Heap Sort           |     |     |   ✓  |     |   ✓    |  ✓   |
|                +---------------------+-----+-----+------+-----+--------+------+
|                | Binary Search       |     |     |   ✓  |     |   ✓    |      |
+----------------+---------------------+-----+-----+------+-----+--------+------+
|                | Heap                |     |  P  |      |     |   ✓    |  ✓   |
|                +---------------------+-----+-----+------+-----+--------+------+
|                | Trie                |     |  ✓  |      |     |        |      |
|                +---------------------+-----+-----+------+-----+--------+------+
| *Trees*        | AVL Tree            |     |     |      |     |        |      |
|                +---------------------+-----+-----+------+-----+--------+------+
|                | Red/Black Tree      |     |     |      |     |        |      |
|                +---------------------+-----+-----+------+-----+--------+------+
|                | Quad Tree           |     |     |      |     |        |      |
+----------------+---------------------+-----+-----+------+-----+--------+------+
|                | Matrix-based graph  |     |     |   ✓  |     |        |      |
|                +---------------------+-----+-----+------+-----+--------+------+
| *Graphs*       | BFS                 |     |     |   ✓  |     |        |      |
|                +---------------------+-----+-----+------+-----+--------+------+
|                | DFS                 |     |     |   ✓  |     |        |      |
+----------------+---------------------+-----+-----+------+-----+--------+------+


Legend:

*   ✓    everything is working
*   P    incomplete/partial implementation


|GaBeacon|_

.. |GaBeacon| image:: https://ga-beacon.appspot.com/UA-184881-14/ads
.. _GaBeacon: https://github.com/igrigorik/ga-beacon
