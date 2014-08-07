"""
Given a sequence of morse code letters and numbers as dashes and dots,
return the equivalent string.
"""

import unittest

# assume international (ITU) morse code
LETTERS_TO_MORSE = {
    "A": ".-",
    "B": "-...",
    "C": "-.-.",
    "D": "-..",
    "E": ".",
    "F": "..-.",
    "G": "--.",
    "H": "....",
    "I": "..",
    "J": ".---",
    "K": "-.-",
    "L": ".-..",
    "M": "--",
    "N": "-.",
    "O": "---",
    "P": ".--.",
    "Q": "--.-",
    "R": ".-.",
    "S": "...",
    "T": "-",
    "U": "..-",
    "V": "...-",
    "W": ".--",
    "X": "-..-",
    "Y": "-.--",
    "Z": "--..",
    "1": ".----",
    "2": "..---",
    "3": "...--",
    "4": "....-",
    "5": ".....",
    "6": "-....",
    "7": "--...",
    "8": "---..",
    "9": "----.",
    "0": "-----",
}

MORSE_TO_LETTERS = {v: k for k, v in LETTERS_TO_MORSE.items()}


def morse(s):
    return "".join([MORSE_TO_LETTERS[letter] for letter in s.split()])


class MorseTest(unittest.TestCase):
    def test_morse(self):
        self.assertEqual("MORSECODE",
                         morse("-- --- .-. ... . -.-. --- -.. ."))
        self.assertEqual("INTERVIEWQUESTION",
                         morse(".. -. - . .-. ...- .. . .-- --.- ..- . ... - .. --- -."))
