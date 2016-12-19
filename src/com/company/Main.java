package com.company;

import spark.Session;
import spark.Spark;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static ArrayList<Person> personData = new ArrayList<>();

    public static void main(String[] args) throws Exception {

        dataFileScan();
        Spark.init();
        Spark.get(                                              //    get(
                "/",                                      //     "/"
                ((request, response) -> {
                    String id = request.queryParams("id");
                    int id = 0;
                })
        );
//        Spark.get(
//                "/person",
//                ((request, response) -> {
//
//                })
//        );
    }

    public static void dataFileScan() throws Exception {
        File dataFile = new File("people.csv");
        Scanner dataFileScanner = new Scanner(dataFile);

        while (dataFileScanner.hasNext()) {
            String line = dataFileScanner.nextLine();
            String[] columns = line.split(",");

            if(!"id".equals(columns[0])) {
                Person person = new Person(Integer.parseInt(columns[0]), columns[1], columns[2], columns[3], columns[4], columns[5]);
                personData.add(person);
            }
        }
    }
}





//    query for "offset"
//    if offset == null
//    content offset to integer
//    ? temp list to hold Person to display
//    Fill temp list using sublist(offset, offset + 20)
//        HashMap to give to HTML
//    put temp list in HashMap
//    if offset >20
//    put root in HashMap (offset + 20)
//        put person in HashMap (offset + 20)
//        return our HTML


