package org.example;

public class postMetod {
    private String name;
    private String job;


    public postMetod (String name,String job){
        this.name=name;
        this.job=job;
    }


    public String getName(){
        return name;
    }
    public void setName(){
        this.name=name;
    }

    public String getjob(){
        return job;
    }
    public void setNjob(){
        this.job=job;
    }
}
