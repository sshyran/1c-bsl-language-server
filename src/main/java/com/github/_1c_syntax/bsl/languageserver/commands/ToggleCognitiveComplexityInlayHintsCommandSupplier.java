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
package com.github._1c_syntax.bsl.languageserver.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github._1c_syntax.bsl.languageserver.codelenses.AbstractMethodComplexityCodeLensSupplier;
import com.github._1c_syntax.bsl.languageserver.inlayhints.CognitiveComplexityInlayHintSupplier;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ToggleCognitiveComplexityInlayHintsCommandSupplier implements CommandSupplier {

  private final CognitiveComplexityInlayHintSupplier complexityInlayHintSupplier;
  private final ObjectMapper objectMapper;

  @Override
  @SneakyThrows
  public Object execute(List<Object> arguments) {
    // todo: refactor as in code lens data
    Object jsonObject = arguments.get(0);
    AbstractMethodComplexityCodeLensSupplier.ComplexityCodeLensData codeLensData;
    if (jsonObject instanceof AbstractMethodComplexityCodeLensSupplier.ComplexityCodeLensData) {
      codeLensData = (AbstractMethodComplexityCodeLensSupplier.ComplexityCodeLensData) jsonObject;
    } else {
      codeLensData = objectMapper.readValue(
        jsonObject.toString(),
        AbstractMethodComplexityCodeLensSupplier.ComplexityCodeLensData.class
      );
    }

    complexityInlayHintSupplier.toggleHints(codeLensData.getUri(), codeLensData.getMethodName());

    return null;
  }
}
