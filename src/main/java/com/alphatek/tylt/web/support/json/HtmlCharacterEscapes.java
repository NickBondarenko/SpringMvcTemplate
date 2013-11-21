package com.alphatek.tylt.web.support.json;

import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.io.CharacterEscapes;

import java.util.Arrays;

public final class HtmlCharacterEscapes extends CharacterEscapes {
  private final int[] asciiEscapes;

  public HtmlCharacterEscapes() {
    // start with set of characters known to require escaping (double-quote, backslash etc)
    int[] esc = CharacterEscapes.standardAsciiEscapesForJSON();
    // and force escaping of a few others:
    esc['<'] = CharacterEscapes.ESCAPE_STANDARD;
    esc['>'] = CharacterEscapes.ESCAPE_STANDARD;
    esc['&'] = CharacterEscapes.ESCAPE_STANDARD;
    esc['\''] = CharacterEscapes.ESCAPE_STANDARD;
    asciiEscapes = esc;
  }

  // this method gets called for character codes 0 - 127
  @Override
  public int[] getEscapeCodesForAscii() {
    return Arrays.copyOf(asciiEscapes, asciiEscapes.length);
  }

  @Override
  public SerializableString getEscapeSequence(int ch) {
    return null;
  }
}