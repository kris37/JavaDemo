package jvm.jannotation;
import java.util.Set;
import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.util.ElementFilter;
import javax.tools.Diagnostic.Kind;

@SupportedAnnotationTypes("foo.CheckGetter")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class CheckGetterProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        // TODO: annotated ElementKind.FIELD
        // //TypeElement指代类或者接口，VariableElement指代字段、局部变量、enum 常量等...ExecutableElement指代方法或者构造器。
        for (TypeElement annotatedClass : ElementFilter.typesIn(roundEnv.getElementsAnnotatedWith(CheckGetter.class))) {

            // 获取 TypeElement 中定义的字段和枚举常量
            //VariableElement指代字段、局部变量、enum 常量
            for (VariableElement field : ElementFilter.fieldsIn(annotatedClass.getEnclosedElements())) {
                if (!containsGetter(annotatedClass, field.getSimpleName().toString())) {
                    processingEnv.getMessager().printMessage(Kind.ERROR,
                            String.format("getter not found for '%s.%s'.", annotatedClass.getSimpleName(), field.getSimpleName()));
                }
            }
        }
        return true;
    }

    private static boolean containsGetter(TypeElement typeElement, String name) {
        String getter = "get" + name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
        //TypeElement.getEnclosedElements方法，获得上面这段代码中Foo类的字段、构造器以及方法。
        for (ExecutableElement executableElement : ElementFilter.methodsIn(typeElement.getEnclosedElements())) {
            if (!executableElement.getModifiers().contains(Modifier.STATIC)
                    && executableElement.getSimpleName().toString().equals(getter)
                    && executableElement.getParameters().isEmpty()) {
                return true;
            }
        }
        return false;
    }
}
