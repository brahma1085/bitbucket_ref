<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<!-- saved from url=(0057)http://www.rabago.net/struts/html2/customHandlingDemo.htm -->
<HTML><HEAD><TITLE>html2 Demo Application - Custom Field Handling Demo</TITLE>
<META http-equiv=Content-Type content="text/html; charset=windows-1252"><LINK 
href="html2 Demo Application - Custom Field Handling Demo_files/html2demo.css" 
type=text/css rel=stylesheet>
<STYLE type=text/css>.invalid {
	BACKGROUND-POSITION: center center; BACKGROUND-IMAGE: url(invalid.gif); BACKGROUND-REPEAT: no-repeat
}
.valid {
	BACKGROUND-POSITION: center center; BACKGROUND-IMAGE: url(valid.gif); BACKGROUND-REPEAT: no-repeat
}
</STYLE>

<META content="MSHTML 6.00.2600.0" name=GENERATOR></HEAD>
<BODY>
<H3>New Employee Data</H3>
<FORM id=employeeForm name=employeeForm 
onsubmit="return validateEmployeeForm(this);" action=index.html#validator>
<TABLE border=0>
  <TBODY>
  <TR>
    <TD><LABEL id=employeeForm.employeeNumber.label 
      for=employeeForm.employeeNumber>Employee Number</LABEL></TD>
    <TD><SPAN 
      id=employeeForm.employeeNumber.mark>&nbsp;&nbsp;&nbsp;&nbsp;</SPAN></TD>
    <TD><INPUT id=employeeForm.employeeNumber 
      onchange="return validateEmployeeForm_employeeNumber(document.employeeForm, this, true, null);" 
      name=employeeNumber></TD>
    <TD><SPAN class=errorStyle1 id=employeeForm.employeeNumber.error 
      style="DISPLAY: none"></SPAN></TD></TR>
  <TR>
    <TD><LABEL id=employeeForm.firstName.label 
      for=employeeForm.firstName>First Name</LABEL></TD>
    <TD><SPAN 
id=employeeForm.firstName.mark>&nbsp;&nbsp;&nbsp;&nbsp;</SPAN></TD>
    <TD><INPUT id=employeeForm.firstName 
      onchange="return validateEmployeeForm_firstName(document.employeeForm, this, true, null);" 
      name=firstName></TD>
    <TD><SPAN class=errorStyle1 id=employeeForm.firstName.error 
      style="DISPLAY: none"></SPAN></TD></TR>
  <TR>
    <TD><LABEL id=employeeForm.lastName.label for=employeeForm.lastName>Last 
      Name</LABEL></TD>
    <TD><SPAN id=employeeForm.lastName.mark>&nbsp;&nbsp;&nbsp;&nbsp;</SPAN></TD>
    <TD><INPUT id=employeeForm.lastName 
      onchange="return validateEmployeeForm_lastName(document.employeeForm, this, true, null);" 
      name=lastName></TD>
    <TD><SPAN class=errorStyle1 id=employeeForm.lastName.error 
      style="DISPLAY: none"></SPAN></TD></TR>
  <TR>
    <TD><LABEL id=employeeForm.department.label 
      for=employeeForm.department>Department</LABEL></TD>
    <TD><SPAN 
    id=employeeForm.department.mark>&nbsp;&nbsp;&nbsp;&nbsp;</SPAN></TD>
    <TD><INPUT id=employeeForm.department 
      onchange="return validateEmployeeForm_department(document.employeeForm, this, true, null);" 
      name=department></TD>
    <TD><SPAN class=errorStyle1 id=employeeForm.department.error 
      style="DISPLAY: none"></SPAN></TD></TR>
  <TR>
    <TD><LABEL id=employeeForm.hireDate.label for=employeeForm.hireDate>Hire 
      Date</LABEL></TD>
    <TD><SPAN id=employeeForm.hireDate.mark>&nbsp;&nbsp;&nbsp;&nbsp;</SPAN></TD>
    <TD><INPUT id=employeeForm.hireDate 
      onchange="return validateEmployeeForm_hireDate(document.employeeForm, this, true, null);" 
      name=hireDate></TD>
    <TD><SPAN class=errorStyle1 id=employeeForm.hireDate.error 
      style="DISPLAY: none"></SPAN></TD></TR>
  <TR>
    <TD><LABEL id=employeeForm.salary.label 
      for=employeeForm.salary>Salary</LABEL></TD>
    <TD><SPAN id=employeeForm.salary.mark>&nbsp;&nbsp;&nbsp;&nbsp;</SPAN></TD>
    <TD><INPUT id=employeeForm.salary 
      onchange="return validateEmployeeForm_salary(document.employeeForm, this, true, null);" 
      name=salary></TD>
    <TD><SPAN class=errorStyle1 id=employeeForm.salary.error 
      style="DISPLAY: none"></SPAN></TD></TR></TBODY></TABLE><INPUT type=submit value="Save Changes"> 
</FORM>
<SCRIPT language=JavaScript1.1 type=text/javascript>
<!-- Begin

    var bCancel = false;

    function validateEmployeeForm(form) {
        if (bCancel) {
            return true;
        }
        var messages = new Array();
        validateEmployeeForm_employeeNumber(form, form["employeeNumber"], messages.length == 0, messages);
        validateEmployeeForm_firstName(form, form["firstName"], messages.length == 0, messages);
        validateEmployeeForm_lastName(form, form["lastName"], messages.length == 0, messages);
        validateEmployeeForm_department(form, form["department"], messages.length == 0, messages);
        validateEmployeeForm_hireDate(form, form["hireDate"], messages.length == 0, messages);
        validateEmployeeForm_salary(form, form["salary"], messages.length == 0, messages);
        if (messages.length > 0) {
            return handleFormErrors(form, messages);
        }
        return true;
    }

    function validateEmployeeForm_employeeNumber(form, field, setFocus, messages) {
        if (!validateRequired(field)) {
            return processError(form, field, -1, null, setFocus, messages, "Employee Number is required.");
        }
        if (!validateInteger(field)) {
            return processError(form, field, -1, null, setFocus, messages, "Employee Number must be an integer.");
        }
        if (window.html2ProcessFormField) {
            html2ProcessFormField(form.name, field.name, true, "")
        }
        return true;
    }

    function validateEmployeeForm_firstName(form, field, setFocus, messages) {
        if (!validateRequired(field)) {
            return processError(form, field, -1, null, setFocus, messages, "First Name is required.");
        }
        if (window.html2ProcessFormField) {
            html2ProcessFormField(form.name, field.name, true, "")
        }
        return true;
    }

    function validateEmployeeForm_lastName(form, field, setFocus, messages) {
        if (!validateRequired(field)) {
            return processError(form, field, -1, null, setFocus, messages, "Last Name is required.");
        }
        if (window.html2ProcessFormField) {
            html2ProcessFormField(form.name, field.name, true, "")
        }
        return true;
    }

    function validateEmployeeForm_department(form, field, setFocus, messages) {
        if (!validateRequired(field)) {
            return processError(form, field, -1, null, setFocus, messages, "Department is required.");
        }
        if (window.html2ProcessFormField) {
            html2ProcessFormField(form.name, field.name, true, "")
        }
        return true;
    }

    function validateEmployeeForm_hireDate(form, field, setFocus, messages) {
        if (!validateRequired(field)) {
            return processError(form, field, -1, null, setFocus, messages, "Hire Date is required.");
        }
        if (!validateDate(field, "MM/dd/yyyy", true)) {
            return processError(form, field, -1, null, setFocus, messages, "Hire Date is not a date.");
        }
        if (window.html2ProcessFormField) {
            html2ProcessFormField(form.name, field.name, true, "")
        }
        return true;
    }

    function validateEmployeeForm_salary(form, field, setFocus, messages) {
        if (!validateRequired(field)) {
            return processError(form, field, -1, null, setFocus, messages, "Salary is required.");
        }
        if (!validateMask(field, eval("/^\\$?\\d+(,\\d{3})*(\\.\\d{2})?$/"))) {
            return processError(form, field, -1, null, setFocus, messages, "Salary is invalid.");
        }
        if (window.html2ProcessFormField) {
            html2ProcessFormField(form.name, field.name, true, "")
        }
        return true;
    }

   /*$RCSfile: RequiredValidator.js,v $ $Revision: 1.1 $ $Date: 2004/11/15 00:00:00 $ */
   /**
    * Check to see that a field does contain a value.
    */
    function validateRequired(field) {
        if (field == null) {
            return false;
        }
        if ((field.type == 'hidden' ||
            field.type == 'text' ||
            field.type == 'textarea' ||
            field.type == 'file' ||
            field.type == 'checkbox' ||
            field.type == 'select-one' ||
            field.type == 'password') &&
            field.disabled == false) {

            var value = '';
            // get field's value
            if (field.type == "select-one") {
                var si = field.selectedIndex;
                if (si >= 0) {
                    value = field.options[si].value;
                }
            } else if (field.type == 'checkbox') {
                if (field.checked) {
                    value = field.value;
                }
            } else {
                value = field.value;
            }

            if (value == null || trim(value).length == 0) {
                return false;
            }
        } else if (field.type == "select-multiple") { 
            var numOptions = field.options.length;
            lastSelected=-1;
            for(loop=numOptions-1;loop>=0;loop--) {
                if(field.options[loop].selected) {
                    lastSelected = loop;
                    value = field.options[loop].value;
                    return true;
                }
            }
            return false;
        } else if ((field.length > 0) && (field[0].type == 'radio' || field[0].type == 'checkbox')) {
            isChecked=-1;
            for (loop=0;loop < field.length;loop++) {
                if (field[loop].checked) {
                    isChecked=loop;
                    break; // only one needs to be checked
                }
            }
            if (isChecked < 0) {
                return false;
            }
        }
        return true;
    }


   /*$RCSfile: IntegerValidator.js,v $ $Revision: 1.1 $ $Date: 2004/11/15 00:00:00 $ */
   /**
    * Check to see if the field is a valid integer.
    */
    function validateInteger(field) {
        var fieldValue = getValueFromField(field);
        if (fieldValue.length > 0) {
            var iValue = parseInt(fieldValue);
            return (!isNaN(iValue) && iValue == fieldValue && iValue >= -2147483648 && iValue <= 2147483647);
        }
        return true;
    }


   /*$RCSfile: MaskValidator.js,v $ $Revision: 1.1 $ $Date: 2004/11/15 00:00:00 $ */
   /**
    * Check to see if the field is valid using a regular expression.
    */
    function validateMask(field, maskExpr) {
        var fieldValue = getValueFromField(field);
        if (fieldValue.length > 0) {
            return maskExpr.exec(fieldValue);
        }
        return true;
    }


   /*$RCSfile: DateValidator.js,v $ $Revision: 1.1 $ $Date: 2004/11/15 00:00:00 $ */
   /**
    * Check to see if the field is a valid date.
    */
    function validateDate(field, datePattern, isStrict) {
        var fieldValue = getValueFromField(field);
        if (fieldValue.length > 0 &&
            datePattern != null && datePattern.length > 0) {

            if (isStrict && datePattern.length != fieldValue.length) {
                return false;
            }
            var dateArray = parseDatePattern(datePattern);
            regExprValue = dateArray[0];
            dateRegExp = new RegExp(regExprValue);
            var matched = dateRegExp.exec(fieldValue);
            if (matched == null) {
                return false;
            }
            var day   = (dateArray[1] < 0) ? 1    : matched[dateArray[1]];
            var month = (dateArray[2] < 0) ? 1    : matched[dateArray[2]];
            var year  = (dateArray[3] < 0) ? 2004 : matched[dateArray[3]];
            
            return isValidDate(day, month, year);
        }
        return true;
    }

    /**
    * Parse a Date Pattern.
    * Returns an Array containing a RegExp to parse
    * the date and information about positions of day, month and year.
    */
    function parseDatePattern(datePattern) {
        dateArray = new Array();
        dateArray[0] = "^";
        dateArray[1] = -1;
        dateArray[2] = -1;
        dateArray[3] = -1;
        var currPos = 1;
        var x = 0;
        while (x < datePattern.length) {
            var currChar = datePattern.charAt(x);
            if ((currChar == 'd' && dateArray[1] < 0) || 
                (currChar == 'M' && dateArray[2] < 0) ||
                (currChar == 'y' && dateArray[3] < 0)) {
                var start = x;
                while (x < datePattern.length && currChar == datePattern.charAt(x)) {
                    x++;
                }
                var lth = x - start;
                if (currChar == 'd') {
                    dateArray[1] = currPos++;
                    dateArray[0] = dateArray[0] + "(\\d{1,2})";
                } else if (currChar == 'M') {
                    dateArray[2] = currPos++;
                    dateArray[0] = dateArray[0] + "(\\d{1,2})";
                } else {
                    dateArray[3] = currPos++;
                    dateArray[0] = dateArray[0] + ((lth > 2) ?  "(\\d{4})" : "(\\d{1,2})");
                }
            } else {
                dateArray[0] = dateArray[0] + "[" + currChar + "]";
                x++;
            }
        }
        dateArray[0] = dateArray[0] + "$";
        return dateArray;
    }

    /**
    * Validate the day, month & year
    */
    function isValidDate(day, month, year) {
	    if (month < 1 || month > 12) {
            return false;
        }
        if (day < 1 || day > 31) {
            return false;
        }
        if ((month == 4 || month == 6 || month == 9 || month == 11) &&
            (day == 31)) {
            return false;
        }
        if (month == 2) {
            var leap = (year % 4 == 0 &&
               (year % 100 != 0 || year % 400 == 0));
            if (day>29 || (day == 29 && !leap)) {
                return false;
            }
        }
        return true;
    }


    /**
     * Get the value from a field.
     */
    function getValueFromField(field) {
        var fieldValue = '';
        if (field != null && field.length > 0 && field[0].type == 'radio') {
            for (var x=0; x < field.length; x++) {
                if (field[x].disabled == false && field[x].checked) {
                    fieldValue = field[x].value;
                    break;
                }
            }
        } else if (field != null && field.disabled == false) {
            if (field.type == 'hidden' || field.type == 'password' ||
                field.type == 'text'   || field.type == 'textarea' ||
                field.type == 'radio'  || field.type == 'file') {
                fieldValue = field.value;
            }
            if (field.type == 'select-one') {
                var si = field.selectedIndex;
                if (si >= 0) {
                    fieldValue = field.options[si].value;
                }
            }
        }
        return fieldValue == null ? '' : fieldValue;
    }

    /**
     * Determine the size of an Indexed Field.
     */
    function findIndexedSize(form, start, indexedProp, prop) {
        var x = start;
        while (true) { 
            var field = form[indexedProp + "[" + x + "]." + prop];
            if (field == null) {
               return x;
            }
            x++;
        }
        return 0;
    }

    /**
     * Process an Error.
     */
    function processError(form, field, idx, indexProp, setFocus, messages, msg) {

        // Set Focus
        if (setFocus && field != null && !(field.type == 'hidden')) {
            field.focus();
        }

        // Replace message $[bean:property] occurances with their values
        msg = replaceMsgProperties(msg, form, idx, indexProp);

        // Set Focus
        if (messages == null) {
            if (window.html2ProcessFormField) {
                html2ProcessFormField(form.name, field.name, false, msg);
            } else {
                handleFieldError(field, msg);
            }
        } else {
            var temp = new Array();
            temp[0] = field.name;
            temp[1] = msg;
            messages[messages.length] = temp;
        }

        return false;
    }

    /**
     * Handle an Individual Field Error.
     */
    function handleFieldError(field, message) {
        alert(message); return false;
    }
 
    /**
     * Handle a set of Form errors.
     */
    function handleFormErrors(form, messages) {
        if (!window.html2ProcessFormField) {
            var errorMessages = new Array();
            for (var i=0; i < messages.length; i++) {
                errorMessages[errorMessages.length] = messages[i][1];
            }
            alert(errorMessages.join('\n')); 
            return false;
        }

        // get the HTML form's elements
        var htmlFormElements = form.elements;
     
        // loop through the elements of the HTML form
        for (var i=0; i < htmlFormElements.length; i++) {
            var htmlFieldNode = htmlFormElements.item(i);
            var attributes = htmlFieldNode.attributes;
            if ((attributes == null) || (attributes.length==0)) {
                continue;
            }
                    
            //var name = htmlFieldNode.attributes.getNamedItem("name");
            var htmlFieldName = htmlFieldNode.name;
            if ((htmlFieldName == null) || (htmlFieldName.length == 0)) {
                // skip those elements without a name
                continue;
            }

            // look for the html field among the messages
            var error = null; 
            for (var j=0; j < messages.length; j++) {
                var temp = messages[j];
                if (temp == null) {
                    // skip null value
                    continue;
                }
                
                var tempFieldName = temp[0];
                if (tempFieldName == htmlFieldName) {
                    // found it
                    error = temp;
                    break; 
                }
            }
            
            var isValid = true;
            var errorMessage = "";
            if (error != null) {
                isValid = false;
                errorMessage = error[1];
            }

            html2ProcessFormField(form.name, htmlFieldName, isValid, errorMessage);
        }
        return false;
    }
 
    /**
     *  replace $[bean:property] occurances with values.
     */
    function replaceMsgProperties(msg, form, idx, indexProp) {
        if (form != null && msg != null) {
            var PREFIX = "$[bean:";
            var start  = 0;
            while (start >= 0) {
                var start = msg.indexOf(PREFIX);
                if (start >= 0) {
                    var end = start + msg.substring(start, msg.length).indexOf(']');
                    if (end < start) {
                        msg = replaceValue(msg, PREFIX, "$[????:");
                    } else {
                        var propName = msg.substring(start + PREFIX.length, end);
                        var othField = form[propName];
                        if (idx >= 0) {
                           var indexedFieldName = indexProp + "[" + idx + "]." + propName;
                           othField = (form[indexedFieldName] == null) ? form[propName] : form[indexedFieldName];
                        }
                        msg = replaceValue(msg, PREFIX + propName + "]", (othField == null) ? "????" : othField.value);
                    }
                }
            }
        }
        return msg;
    }
 
    /**
     *  Replace a value.
     */
    function replaceValue(inputValue, fromValue, toValue) {
        var pos = inputValue.indexOf(fromValue);
        return (pos < 0) ? inputValue : inputValue.substring(0, pos) + toValue + 
                                        inputValue.substring(pos + fromValue.length, inputValue.length);
    }
    
    /**
     * Trim whitespace from left and right sides of s.
     */
    function trim(s) {return s.replace( /^\s*/, "" ).replace( /\s*$/, "" );}
    


//End -->
</SCRIPT>

<SCRIPT language=JavaScript1.1 type=text/javascript>
<!-- Begin

function html2ProcessFormField(formName, fieldName, isValid, errorMessage) {
   processField(formName, fieldName, isValid, errorMessage);
}


//End -->
</SCRIPT>

<SCRIPT language=Javascript type=text/javascript>
function processField(formName, fieldName, isValid, errorMessage) {
    var id = formName + "." + fieldName + ".mark";
    var element = document.getElementById(id);
    if (element != null) {
        if (isValid) {
            element.className = "valid";
        } else {
            element.className = "invalid";
        }
    }
    id = formName + "." + fieldName + ".error";
    element = document.getElementById(id);
    if (element != null) {
        if (isValid) {
            element.innerHTML = "";
            element.style.display = "none";
        } else {
            element.innerHTML = errorMessage;
            element.style.display = "inline";
        }
    }
}
</SCRIPT>
This page demonstrates custom handling of valid and invalid fields. In this demo 
page, the fields that are invalid are shown with an "x" mark. </BODY></HTML>
