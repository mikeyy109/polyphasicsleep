package com.liquications.polyphasicsleep;

/**
 * Created by Mikey on 26/07/2014.
 */
public class Poly {

    public String nameOfPoly;
    public String detailOfPoly;
    public String nameOfImage;
    public String popUp;

    public Poly(String startOfPoly, String startOfDetail, String startOfImage, String startPopUp){
        this.nameOfPoly = startOfPoly;
        this.detailOfPoly = startOfDetail;
        this.nameOfImage = startOfImage;
        this.popUp = startPopUp;
    }
}
