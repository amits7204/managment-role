package com.example.jeevanjyoti.volunteerPojo;

public class VolunteerData {
        private String name;

        private String image_url;

        private String gender;

        private String mobile;

        private String address;

        public void setName(String name){
            this.name = name;
        }
        public String getName(){
            return this.name;
        }
        public void setImage_url(String image_url){
            this.image_url = image_url;
        }
        public String getImage_url(){
            return this.image_url;
        }
        public void setGender(String gender){
            this.gender = gender;
        }
        public String getGender(){
            return this.gender;
        }
        public void setMobile(String mobile){
            this.mobile = mobile;
        }
        public String getMobile(){
            return this.mobile;
        }
        public void setAddress(String address){
            this.address = address;
        }
        public String getAddress(){
            return this.address;
        }
    }

