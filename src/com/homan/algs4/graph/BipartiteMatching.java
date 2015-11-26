package com.homan.algs4.graph;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.StdOut;
import javafx.util.Pair;

public class BipartiteMatching {

    private ST<String, Integer> students;
    private ST<String, Integer> companies;
    private Bag<Pair<String, String>> studentsWant;
    private Bag<Pair<String, String>> companiesOffer;

    private FlowNetwork flowNetwork;
    private FordFulkerson fordFulkerson;

    public BipartiteMatching() {

        initStudents();
        initCompanies();
        initStudentsWant();
        initCompaniesOffer();
        buildFlowNetwork();
        fordFulkerson = new FordFulkerson(flowNetwork, s(), t());
    }

    public double flow() {
        return fordFulkerson.value();
    }

    private void buildFlowNetwork() {
        flowNetwork = new FlowNetwork(students.size() + companies.size() + 2);

        for (String student : students) {
            flowNetwork.addEdge(new FlowEdge(s(), stud(students.get(student)), 1));
        }

        for (Pair<String, String> studentWants : studentsWant) {
            flowNetwork.addEdge(new FlowEdge(stud(students.get(studentWants.getKey())), comp(companies.get(studentWants.getValue())), Integer.MAX_VALUE));
        }

        for (Pair<String, String> companyOffers : companiesOffer) {
            flowNetwork.addEdge(new FlowEdge(comp(companies.get(companyOffers.getKey())), stud(students.get(companyOffers.getValue())), Integer.MAX_VALUE));
        }

        for (String company : companies) {
            flowNetwork.addEdge(new FlowEdge(comp(companies.get(company)), t(), 1));
        }
    }

    private int stud(int i) {
        return i;
    }

    private int comp(int i) {
        return students.size() + i;
    }

    private int s() {
        return flowNetwork.V() - 2;
    }

    private int t() {
        return flowNetwork.V() - 1;
    }

    private void initCompaniesOffer() {
        companiesOffer = new Bag<>();
        companiesOffer.add(new Pair<>("Adobe", "Alice"));
        companiesOffer.add(new Pair<>("Adobe", "Bob"));
        companiesOffer.add(new Pair<>("Adobe", "Carol"));
        companiesOffer.add(new Pair<>("Amazon", "Alice"));
        companiesOffer.add(new Pair<>("Amazon", "Bob"));
        companiesOffer.add(new Pair<>("Amazon", "Dave"));
        companiesOffer.add(new Pair<>("Amazon", "Eliza"));
        companiesOffer.add(new Pair<>("Facebook", "Carol"));
        companiesOffer.add(new Pair<>("Google", "Alice"));
        companiesOffer.add(new Pair<>("Google", "Carol"));
        companiesOffer.add(new Pair<>("Yahoo", "Dave"));
        companiesOffer.add(new Pair<>("Yahoo", "Eliza"));
    }

    private void initStudentsWant() {
        studentsWant = new Bag<>();
        studentsWant.add(new Pair<>("Alice", "Adobe"));
        studentsWant.add(new Pair<>("Alice", "Amazon"));
        studentsWant.add(new Pair<>("Alice", "Google"));
        studentsWant.add(new Pair<>("Bob", "Adobe"));
        studentsWant.add(new Pair<>("Bob", "Amazon"));
        studentsWant.add(new Pair<>("Carol", "Adobe"));
        studentsWant.add(new Pair<>("Carol", "Facebook"));
        studentsWant.add(new Pair<>("Carol", "Google"));
        studentsWant.add(new Pair<>("Dave", "Amazon"));
        studentsWant.add(new Pair<>("Dave", "Yahoo"));
        studentsWant.add(new Pair<>("Eliza", "Amazon"));
        studentsWant.add(new Pair<>("Eliza", "Yahoo"));
    }

    private void initCompanies() {
        companies = new ST<>();
        companies.put("Adobe", 0);
        companies.put("Amazon", 1);
        companies.put("Facebook", 2);
        companies.put("Google", 3);
        companies.put("Yahoo", 4);
    }

    private void initStudents() {
        students = new ST<>();
        students.put("Alice", 0);
        students.put("Bob", 1);
        students.put("Carol", 2);
        students.put("Dave", 3);
        students.put("Eliza", 4);
    }

    public static void main(String[] args) {
        BipartiteMatching matching = new BipartiteMatching();
        StdOut.printf("Flow in the network: %f\n", matching.flow());
    }
}
