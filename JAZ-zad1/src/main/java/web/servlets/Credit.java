package web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static java.lang.Math.pow;

@WebServlet("/credit")
public class Credit extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        java.text.DecimalFormat df = new java.text.DecimalFormat();
        df.setMaximumFractionDigits(2);
        df.setMinimumFractionDigits(0);

        float totalValueOfLoan = Float.parseFloat(request.getParameter("totalvalue"));
        int numberOfInstallments = Integer.parseInt(request.getParameter("number"));
        float interest = Float.parseFloat(request.getParameter("interest"));
        String type = request.getParameter("type");
        float fixedFee = Float.parseFloat(request.getParameter("fixedFee"));

        float fixedFees = getFixedFee(fixedFee, numberOfInstallments);


        response.setContentType("text/html");
        response.getWriter().print("<table border=\"1\">" +
                "<tr>" +
                "<td> nr raty </td>" +
                "<td> kwota kapitalu </td>" +
                "<td> kwota odsetek </td>" +
                "<td> Oplata Stala </td>" +
                "<td> Calkowita kwota raty </td>" +
                "</tr>");

        if (type.equals("decreasing")) {
            for (int i = 1; i <= numberOfInstallments; i++) {
                float decreasingCapitalAmount = getDecreasingCapitalAmount(totalValueOfLoan, numberOfInstallments);
                float decreasingTotalAmountOfInstallment = getDecreasingTotalAmountOfInstallment(totalValueOfLoan, numberOfInstallments, interest, i);
                float decreasingInterestAmount = getDecreasingInterestAmount(decreasingTotalAmountOfInstallment, decreasingCapitalAmount);

                response.getWriter().print("<tr>" +
                        "<td>" + i + "</td>" +
                        "<td>" + df.format(decreasingCapitalAmount) + "</td>" +
                        "<td>" + df.format(decreasingInterestAmount) + "</td>" +
                        "<td>" + df.format(fixedFees) + "</td>" +
                        "<td>" + df.format(decreasingTotalAmountOfInstallment + fixedFees) + "</td>" +
                        "<td></td>" +
                        "</tr>");

            }
        } else if (type.equals("static")) {
            for (int i = 1; i <= numberOfInstallments; i++) {
                float staticTotalAmountOfInstallment = getStaticTotalAmountOfInstallment(totalValueOfLoan, interest, numberOfInstallments);
                float staticCapitalAmount = getStaticCapitalAmount(totalValueOfLoan, numberOfInstallments, i, interest);
                float StaticInterestAmount = getStaticInterestAmount(totalValueOfLoan, interest, numberOfInstallments, i);

                response.getWriter().print("<tr>" +
                        "<td>" + i + "</td>" +
                        "<td>" + df.format(staticCapitalAmount) + "</td>" +
                        "<td>" + df.format(StaticInterestAmount) + "</td>" +
                        "<td>" + df.format(fixedFees) + "</td>" +
                        "<td>" + df.format(staticTotalAmountOfInstallment + fixedFees) + "</td>" +
                        "<td></td>" +
                        "</tr>");

            }
        }

    }


    public float getDecreasingTotalAmountOfInstallment(float totalAmountOfInstallment, int numberOfInstallments, float interest, int whichInstallment) {
        return (float) ((totalAmountOfInstallment / numberOfInstallments) * (1 + (((numberOfInstallments - whichInstallment) + 1) * ((0.01 * interest) / 12))));
    }

    public float getDecreasingInterestAmount(float totalAmountOfInstallment, float capitalAmount) {
        return (totalAmountOfInstallment - capitalAmount);
    }

    public float getDecreasingCapitalAmount(float totalValueOfLoan, int numberOfInstallments) {
        return totalValueOfLoan / numberOfInstallments;
    }

    public float getFixedFee(float fixedFee, int numberOfInstallments) {
        return fixedFee / numberOfInstallments;
    }

    public float getStaticTotalAmountOfInstallment(float totalValueOfLoan, float interest, int numberOfInstallments) {
        float factor = (((interest) / 100) / 12) + 1;
        return (float) (totalValueOfLoan * ((pow(factor, numberOfInstallments) * (factor - 1)) / (pow(factor, numberOfInstallments) - 1)));
    }

    public float getStaticInterestAmount(float totalValueOfLoan, float interest, int numberOfInstallments, int whichInstallment) {
        float factor = (((interest) / 100) / 12) + 1;
        return (float) (totalValueOfLoan * ((pow(factor, numberOfInstallments) - pow(factor, whichInstallment - 1)) / ((pow(factor, numberOfInstallments)) - 1)) * ((interest) / 100 / 12));
    }

    public float getStaticCapitalAmount(float totalValueOfLoan, int numberOfInstallments, int whichInstallment, float interest) {
        float factor = (((interest) / 100) / 12) + 1;
        return (float) (totalValueOfLoan * (pow(factor, whichInstallment) - pow(factor, whichInstallment - 1)) / (pow(factor, numberOfInstallments) - 1));
    }
}