package com.company;

import spark.ModelAndView;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

//use ForumWeb as a reference//
public class Main {

    public static ArrayList<Person> personData = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        dataFileScan();
        Spark.init();
        Spark.get(
                "/",
                ((request, response) -> {
                    String offsetStr = request.queryParams("offset");

                    if (offsetStr == null) {
                        offsetStr = "0";
                    }
                    int offset = Integer.parseInt(offsetStr);

                    ArrayList<Person> peopleList = getListing(offset, offset + 20);



                    HashMap forPeopleList = new HashMap();
                    forPeopleList.put("peopleList", peopleList);

                    if (offset + 20 < personData.size()) {
                        forPeopleList.put("nextOffset", offset + 20);
                    }
                    if (offset - 20 >= 0) {
                        forPeopleList.put("prevOffset", offset - 20);
                    }
                    return new ModelAndView(forPeopleList, "index.html");
                }),
                new MustacheTemplateEngine()
        );

        Spark.get(
                "/person",
                ((request, response) -> {
                    HashMap individualPerson = new HashMap();
                    int id = Integer.valueOf(request.queryParams("id"));
                    Person person = personData.get(id-1);


                    return new ModelAndView(person,"person.html");
                }),
                new MustacheTemplateEngine()
        );
    }


    public static void dataFileScan() throws Exception {
        File dataFile = new File("people.csv");
        Scanner dataFileScanner = new Scanner(dataFile);

        while (dataFileScanner.hasNext()) {
            String line = dataFileScanner.nextLine();
            String[] columns = line.split(",");

            if (!"id".equals(columns[0])) {
                Person person = new Person(Integer.parseInt(columns[0]), columns[1], columns[2], columns[3], columns[4], columns[5]);
                personData.add(person);
            }
        }
    }

    /**
     * this method returns a sublist of people starting at the startIndex ending at the endIndex
     *
     * @param startIndex
     * @param endIndex
     * @return
     */
    private static ArrayList<Person> getListing(int startIndex, int endIndex) {
        ArrayList<Person> theReturn = new ArrayList<>();
        while (startIndex < endIndex) {
            theReturn.add(personData.get(startIndex));
            startIndex++;
        }
        return theReturn;
    }
}


