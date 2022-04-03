package com.github._1c_syntax.bsl.languageserver.commands;

import com.github._1c_syntax.bsl.languageserver.codelenses.AbstractMethodComplexityCodeLensSupplier;
import com.github._1c_syntax.bsl.languageserver.inlayhints.AbstractComplexityInlayHintSupplier;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.Value;

import java.beans.ConstructorProperties;
import java.net.URI;
import java.util.Optional;

@RequiredArgsConstructor
public abstract class AbstractToggleComplexityInlayHintsCommandSupplier
  implements CommandSupplier<AbstractToggleComplexityInlayHintsCommandSupplier.ToggleComplexityInlayHintsCommandArguments>
{
  private final AbstractComplexityInlayHintSupplier complexityInlayHintSupplier;

  @Override
  public Class<ToggleComplexityInlayHintsCommandArguments> getCommandArgumentsClass() {
    return ToggleComplexityInlayHintsCommandArguments.class;
  }

  @Override
  public Optional<Object> execute(ToggleComplexityInlayHintsCommandArguments arguments) {
    complexityInlayHintSupplier.toggleHints(arguments.getUri(), arguments.getMethodName());
    return Optional.empty();
  }

  @Override
  public boolean refreshInlayHintsAfterExecuteCommand() {
    return true;
  }

  @Value
  @EqualsAndHashCode(callSuper = true)
  @ToString(callSuper = true)
  public static class ToggleComplexityInlayHintsCommandArguments extends DefaultCommandArguments {
    /**
     * Имя метода.
     */
    String methodName;

    @ConstructorProperties({"uri", "id", "methodName"})
    public ToggleComplexityInlayHintsCommandArguments(URI uri, String id, String methodName) {
      super(uri, id);
      this.methodName = methodName;
    }

    public ToggleComplexityInlayHintsCommandArguments(String id, AbstractMethodComplexityCodeLensSupplier.ComplexityCodeLensData data) {
      this(data.getUri(), id, data.getMethodName());
    }
  }
}
