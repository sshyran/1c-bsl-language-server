package com.github._1c_syntax.bsl.languageserver.commands;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.net.URI;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.EXISTING_PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = NAME, include = EXISTING_PROPERTY, property = "id", visible = true)
public interface CommandArguments {
  URI getUri();
  String getId();
}
