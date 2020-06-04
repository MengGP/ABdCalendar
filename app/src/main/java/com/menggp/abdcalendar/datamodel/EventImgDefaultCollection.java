package com.menggp.abdcalendar.datamodel;

import com.menggp.abdcalendar.R;

import java.util.ArrayList;

/*
    Класс описывает коллекцию изображений событий - из Drowable
 */
public class EventImgDefaultCollection {

    ArrayList<Integer> imgCollection = new ArrayList<>();

    public EventImgDefaultCollection() {
        this.imgCollection.add(R.drawable.a01_ev_img_balloon);
        this.imgCollection.add(R.drawable.a02_ev_img_batman_logo);
        this.imgCollection.add(R.drawable.a03_ev_img_bear_face);
        this.imgCollection.add(R.drawable.a04_ev_img_black_blood_face);
        this.imgCollection.add(R.drawable.a05_ev_img_black_cat);
        this.imgCollection.add(R.drawable.a06_ev_img_cat_face);
        this.imgCollection.add(R.drawable.a07_ev_img_christmas_tree);
        this.imgCollection.add(R.drawable.a08_ev_img_default);
        this.imgCollection.add(R.drawable.a09_ev_img_dog_face);
        this.imgCollection.add(R.drawable.a10_ev_img_elf_face);
        this.imgCollection.add(R.drawable.a11_ev_img_finn_the_human);
        this.imgCollection.add(R.drawable.a12_ev_img_jack_o_lantern);
        this.imgCollection.add(R.drawable.a13_ev_img_monkey_face);
        this.imgCollection.add(R.drawable.a14_ev_img_skull_face);
        this.imgCollection.add(R.drawable.a15_ev_img_tiger_face);
    } // end_consructor

    // --- getters and setters
    public ArrayList<Integer> getImgCollection() {
        return imgCollection;
    }
    // -- end_getters_setters

} // end_class
