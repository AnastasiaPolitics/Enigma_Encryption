package enigma;

import java.util.Map;
import java.util.HashMap;

import java.util.Set;
import java.util.Map.Entry;

import static enigma.EnigmaException.*;

/** Represents a permutation of a range of integers starting at 0 corresponding
 *  to the characters of an alphabet.
 *  @author Anastasia Sukhorebraya
 */
class Permutation {

    /** Set this Permutation to that specified by CYCLES, a string in the
     *  form "(cccc) (cc) ..." where the c's are characters in ALPHABET, which
     *  is interpreted as a permutation in cycle notation.  Characters in the
     *  alphabet that are not included in any cycle map to themselves.
     *  Whitespace is ignored. */

    Permutation(String cycles, Alphabet alphabet) {

        _alphabet = alphabet;
        dictionary = new HashMap<Character, Character>();
        invDictionary = new HashMap<Character, Character>();

        String[] toSplit = cycles.split("\\)");
        for (String cycle: toSplit) {
            cycle = cycle.replaceAll("\\(", "");
            cycle = cycle.replaceAll(" ", "");
            cycle = cycle.replaceAll("\\)", "");
            cycle = cycle.replaceAll("[)(]", "");

            addCycle(cycle);
        }

        for (int i = 0; i < _alphabet.size(); i++) {
            if (!dictionary.containsKey(_alphabet.toChar(i))) {
                dictionary.put(_alphabet.toChar(i), _alphabet.toChar(i));
                invDictionary.put(_alphabet.toChar(i), _alphabet.toChar(i));
            }
        }
    }

    /** Add the cycle c0->c1->...->cm->c0 to the permutation, where CYCLE is
     *  c0c1...cm. */
    private void addCycle(String cycle) {

        for (int i = 0; i < cycle.length(); i++) {
            if (i  == cycle.length() - 1) {
                dictionary.put(cycle.charAt(i), cycle.charAt(0));
                invDictionary.put(cycle.charAt(0), cycle.charAt(i));

            } else {
                dictionary.put(cycle.charAt(i), cycle.charAt(i + 1));
                invDictionary.put(cycle.charAt(i + 1), cycle.charAt(i));

            }
        }
    }

    /** Return the value of P modulo the size of this permutation. */
    final int wrap(int p) {
        int r = p % size();
        if (r < 0) {
            r += size();
        }
        return r;
    }

    /** Returns the size of the alphabet I permute. */
    int size() {
        return _alphabet.size();
    }

    /** Return the result of applying this permutation to P modulo the
     *  alphabet size. */
    int permute(int p) {

        char out = permute(_alphabet.toChar(wrap(p)));
        return _alphabet.toInt(out);
    }

    /** Return the result of applying the inverse of this permutation
     *  to  C modulo the alphabet size. */
    int invert(int c) {
        char inChar = _alphabet.toChar(wrap(c));
        return _alphabet.toInt(invert(inChar));
    }

    /** Return the result of applying this permutation to the index of P
     *  in ALPHABET, and converting the result to a character of ALPHABET. */
    char permute(char p) {
        return (char) dictionary.get(p);
    }

    /** Return the result of applying the inverse of this permutation to C. */
    char invert(char c) {
        return (char) invDictionary.get(c);
    }

    /** Return the alphabet used to initialize this Permutation. */
    Alphabet alphabet() {
        return _alphabet;
    }

    /** Return true iff this permutation is a derangement (i.e., a
     *  permutation for which no value maps to itself). */
    boolean derangement() {

        Set<Map.Entry<Character, Character>> entrySet = dictionary.entrySet();

        for (Entry pair: entrySet) {
            if (pair.getValue() == pair.getKey()) {
                return false;
            }
        }
        return true;
    }

    /** Alphabet of this permutation. */
    private Alphabet _alphabet;

    /** Dictionary of permutations in the forward direction. */
    private HashMap<Character, Character> dictionary;

    /** Dictionary of permutations in the backward direction. */
    private HashMap<Character, Character> invDictionary;
}
