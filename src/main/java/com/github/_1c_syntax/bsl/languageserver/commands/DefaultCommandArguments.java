package com.github._1c_syntax.bsl.languageserver.commands;

import com.github._1c_syntax.bsl.languageserver.databind.URITypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import lombok.Value;
import lombok.experimental.NonFinal;

import java.net.URI;

@Value
@NonFinal
public class DefaultCommandArguments implements CommandArguments {
  @JsonAdapter(URITypeAdapter.class)
  URI uri;
  String id;

}
