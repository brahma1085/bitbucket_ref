<%@ taglib prefix="demo" tagdir="/WEB-INF/tags/demo" %>

<demo:fragmentAttr attr1="value1" attr2="value2">
    <jsp:attribute name="template">
        <p> Template: ${data} </p>
    </jsp:attribute>
    <jsp:body>
        <p> Body Content: ${data} </p>
    </jsp:body>
</demo:fragmentAttr>
