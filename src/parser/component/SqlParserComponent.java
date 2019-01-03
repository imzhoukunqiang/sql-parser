package parser.component;

import com.intellij.openapi.components.ApplicationComponent;
import org.jetbrains.annotations.NotNull;

/**
 * Created by zkq on 2018/1/11 16:24.
 */
public class SqlParserComponent implements ApplicationComponent {


    public SqlParserComponent() {
    }

    @Override
    public void initComponent() {
    }

    @Override
    public void disposeComponent() { // TODO: insert parser.component disposal logic here
    }

    @Override
    @NotNull
    public String getComponentName() {
        return "SqlParserComponent";
    }
 }