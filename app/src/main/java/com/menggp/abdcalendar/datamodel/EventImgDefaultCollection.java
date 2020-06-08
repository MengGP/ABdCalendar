package com.menggp.abdcalendar.datamodel;

import com.menggp.abdcalendar.R;

import java.util.ArrayList;

/*
    Класс описывает коллекцию изображений событий - из Drowable
 */
public class EventImgDefaultCollection {

    ArrayList<Integer> imgCollection = new ArrayList<>();

    public EventImgDefaultCollection() {
        this.imgCollection.add(R.drawable.a01_ev_img_default);
        this.imgCollection.add(R.drawable.a02_ev_img_ballon);
        this.imgCollection.add(R.drawable.a03_ev_img_bender);
        this.imgCollection.add(R.drawable.a04_ev_img_bear_face);
        this.imgCollection.add(R.drawable.a05_ev_img_black_cat);
        this.imgCollection.add(R.drawable.a06_ev_img_cat_face);
        this.imgCollection.add(R.drawable.a07_ev_img_christmas_tree);
        this.imgCollection.add(R.drawable.a08_ev_img_dog_face);
        this.imgCollection.add(R.drawable.a09_ev_img_elf_face);
        this.imgCollection.add(R.drawable.a10_ev_img_finn_the_human);
        this.imgCollection.add(R.drawable.a11_ev_img_jack_o_lantern);
        this.imgCollection.add(R.drawable.a12_ev_img_monkey_face);
        this.imgCollection.add(R.drawable.a13_ev_img_brave_soul);
        this.imgCollection.add(R.drawable.a14_ev_img_woomen);
        this.imgCollection.add(R.drawable.a15_ev_img_witch);
        this.imgCollection.add(R.drawable.a16_ev_img_unicorn);
        this.imgCollection.add(R.drawable.a17_ev_img_da_vinchi);
        this.imgCollection.add(R.drawable.a18_ev_img_pixel_star);
        this.imgCollection.add(R.drawable.a19_ev_img_confetti);
        this.imgCollection.add(R.drawable.a20_ev_img_news);
        this.imgCollection.add(R.drawable.a21_ev_img_safety_circle);
        this.imgCollection.add(R.drawable.a22_ev_img_dali);
        this.imgCollection.add(R.drawable.a23_ev_img_card);
        this.imgCollection.add(R.drawable.a24_ev_img_ballon2);
        this.imgCollection.add(R.drawable.a25_ev_img_fire);
    } // end_consructor

    // --- getters and setters
    public ArrayList<Integer> getImgCollection() {
        return imgCollection;
    }
    // -- end_getters_setters

} // end_class
