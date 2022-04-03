/*
 * This file is a part of BSL Language Server.
 *
 * Copyright (c) 2018-2022
 * Alexey Sosnoviy <labotamy@gmail.com>, Nikita Fedkin <nixel2007@gmail.com> and contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 *
 * BSL Language Server is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
 *
 * BSL Language Server is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with BSL Language Server.
 */
package com.github._1c_syntax.bsl.languageserver.commands.databind;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.github._1c_syntax.bsl.languageserver.commands.CommandArguments;
import com.github._1c_syntax.bsl.languageserver.commands.CommandSupplier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommandArgumentsObjectMapper extends ObjectMapper {

  private static final long serialVersionUID = 5095740064999651671L;

  public CommandArgumentsObjectMapper(List<CommandSupplier<? extends CommandArguments>> commandSuppliers) {
    super();

    commandSuppliers.stream()
      .map(CommandArgumentsObjectMapper::toNamedType)
      .forEach(this::registerSubtypes);
  }

  private static NamedType toNamedType(CommandSupplier<? extends CommandArguments> commandSupplier) {
    return new NamedType(commandSupplier.getCommandArgumentsClass(), commandSupplier.getId());
  }
}
