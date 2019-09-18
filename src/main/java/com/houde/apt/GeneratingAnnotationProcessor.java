package com.houde.apt;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.Writer;
import java.util.Set;

/**
 * @author qiukun
 * @create 2018-05-04 11:12
 **/
@SupportedAnnotationTypes("com.houde.apt.Immutable")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class GeneratingAnnotationProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (final Element element : roundEnv.getElementsAnnotatedWith(Immutable.class)) {
            if (element instanceof TypeElement) {
                final TypeElement typeElement = (TypeElement) element;
                final PackageElement packageElement =
                        (PackageElement) typeElement.getEnclosingElement();

                try {
                    final String className = typeElement.getSimpleName() + "Immutable";
                    final JavaFileObject fileObject = processingEnv.getFiler().createSourceFile(
                            packageElement.getQualifiedName() + "." + className);

                    try (Writer writter = fileObject.openWriter()) {
                        writter.append("package " + packageElement.getQualifiedName() + ";");
                        writter.append("\\n\\n");
                        writter.append("public class " + className + " {");
                        writter.append("\\n");
                        writter.append("}");
                    }
                } catch (final IOException ex) {
                    processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, ex.getMessage());
                }
            }
        }

        // Claiming that annotations have been processed by this processor
        return true;
    }
}

