package com.website.hosting;

import java.util.HashMap;

import com.sun.net.httpserver.HttpServer;

public class App {
    public static void main( String[] args ) {

        HashMap<String, String> listOfHtmlPages = new HashMap<>() {
            {
                put("/", "HtmlStranica/mainPage.html");
            }
        };

        HashMap<String, String> listOfCssPages = new HashMap<>() {
            {
                put("/mainPageStyle.css", "HtmlStranica/mainPageStyle.css");
            }
        };

        HashMap<String, String> listOfJsPages = new HashMap<>() {
            {
                put("/navbarScrollToDiv.js", "HtmlStranica/navbarScrollToDiv.js");
                put("/scrollbarHideNav.js", "HtmlStranica/scrollbarHideNav.js");
            }
        };

        HashMap<String, String> listOfImages = new HashMap<>() {
            {
                put("/slike/firstSlide.png", "HtmlStranica/slike/firstSlide.png");
                put("/slike/croppedBootstrapLogo.png", "HtmlStranica/slike/croppedBootstrapLogo.png");
                put("/slike/bootstrapLogo.png", "HtmlStranica/slike/bootstrapLogo.png");
                put("/slike/fifthSlide.jpg", "HtmlStranica/slike/fifthSlide.jpg");
                put("/slike/firstImageInstallation.jpg", "HtmlStranica/slike/firstImageInstallation.jpg");
                put("/slike/fourthImageInstallation.jpg", "HtmlStranica/slike/fourthImageInstallation.jpg");
                put("/slike/fourthImageInstallation.png", "HtmlStranica/slike/fourthImageInstallation.png");
                put("/slike/secondImageInstallation.png", "HtmlStranica/slike/secondImageInstallation.png");
                put("/slike/secondSlide.png", "HtmlStranica/slike/secondSlide.png");
                put("/slike/thirdSlide.png", "HtmlStranica/slike/thirdSlide.png");
            }
        };

        HttpServer serverThread = HttpSrv.startServerThread(25652, listOfHtmlPages, listOfCssPages, listOfJsPages, listOfImages);

    }
}