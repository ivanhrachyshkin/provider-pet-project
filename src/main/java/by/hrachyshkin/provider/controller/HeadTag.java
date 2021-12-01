package by.hrachyshkin.provider.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class HeadTag extends TagSupport {

    private static final Logger LOGGER = LoggerFactory.getLogger(HeadTag.class);

    @Override
    public int doStartTag() throws JspException {

        try {
            pageContext.getOut().write("<title>Provider</title>\n" +
                    "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                    "<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\" integrity=\"sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u\" crossorigin=\"anonymous\">\n" +
                    "\n" +
                    "<style type=\"text/css\">\n" +
                    "    #error {\n" +
                    "        position: fixed;\n" +
                    "        left: 0;\n" +
                    "        bottom: 0;\n" +
                    "        padding-top: 10px;\n" +
                    "        width: 30%;\n" +
                    "    }\n" +
                    "\n" +
                    "    #footer {\n" +
                    "        left: 0;\n" +
                    "        bottom: 0;\n" +
                    "        width: 100%;\n" +
                    "    }\n" +
                    "\n" +
                    "    input {\n" +
                    "        max-width: 150px;\n" +
                    "    }\n" +
                    "\n" +
                    "    @media (max-width: 400px) {\n" +
                    "        .table-hover {\n" +
                    "            font-size:10px !important;\n" +
                    "        }\n" +
                    "    }\n" +
                    "\n" +
                    "</style>");
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            throw new JspException();
        }
        return EVAL_BODY_INCLUDE;
    }

    @Override
    public int doEndTag() {
        return SKIP_BODY;
    }
}
