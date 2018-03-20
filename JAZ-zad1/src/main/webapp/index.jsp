<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Credit Form</title>
</head>
<body>
<form action="credit" method="POST">
    <p> Wnioskowana kwota kredytu:</p>
    <label><input type="number" id="totalvalue" name="totalvalue" min="1000" required/>zl</label><br>
    <p>Ilosc rat:</p>
    <label><input type="number" id="number" name="number" min="1" required/></label><br>
    <p>Oprocentowanie:</p>
    <label> <input type="number" id="interest" name="interest" min="1" required/>%</label>
    <p>Rodzaj rat </p>
    <label>Malejace<input type="radio" id="decreasing" name="type" value="decreasing" required/></label>
    <label>Stale<input type="radio" id="static" name="type" value="static" required/><br></label><br>
    <p>Oplata stala:</p>
    <label> <input type="number" id="fixedFee" name="fixedFee" min="1" required/>zl</label><br>
    <input type="submit" value="wyslij"/>
</form>
</body>
</html>
