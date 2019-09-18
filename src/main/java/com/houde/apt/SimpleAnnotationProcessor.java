package com.houde.apt;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.tools.Diagnostic;
import java.util.Set;

/**
 * @author qiukun
 * @create 2018-05-04 10:54
 **/
@SupportedAnnotationTypes("com.houde.apt.Immutable")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class SimpleAnnotationProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        //process 函数中参数 annotations 表示待处理的 Annotations，参数 roundEnv 表示当前或是之前的运行环境
        for (final Element element : roundEnv.getElementsAnnotatedWith(Immutable.class)) {
            if (element instanceof TypeElement) {
                final TypeElement typeElement = (TypeElement) element;

                for (final Element eclosedElement : typeElement.getEnclosedElements()) {
                    if (eclosedElement instanceof VariableElement) {
                        final VariableElement variableElement = (VariableElement) eclosedElement;
                        if (!variableElement.getModifiers().contains(Modifier.FINAL)) {
                            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR,
                                    String.format("Class '%s' is annotated as @Immutable,but field '%s' is not declared as final ",
                                            typeElement.getSimpleName(), variableElement.getSimpleName())
                            );
                        }
                    }
                }
            }
        }
        // Claiming that annotations have been processed by this processor
        return true;
    }


}