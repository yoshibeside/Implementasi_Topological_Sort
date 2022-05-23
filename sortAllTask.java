//Java program to print all topological sorts of a graph
import java.util.*;

import javax.sound.midi.Soundbank;


class Node {
    int number;
    String data;

    public Node (int number, String data) {
        this.number = number;
        this.data = data;
    }

    public int getNumber() {
        return number;
    }

    public String getData() {
        return data;
    }
}

class Data {
    String data;
    Integer score;

    public Data (String data) {
        this.data = data;
        this.score = 0;
    }

    public void setScore(Integer degree) {
        System.out.println(degree);
        this.score += degree;
    }

    public Integer getScore() {
        return this.score;
    }

    public boolean equals(Data other) {
        if ((other == null) || (getClass() != other.getClass())){
            return false;
        }
        else {
            Data otherData = (Data) other;
            return (data.equals(otherData.data) && score.equals(otherData.score));
        }
    }

    public String toString() {
        return this.data;
    }
}
class Graph {
    int V; // No. of vertices
 
    List<Integer> adjListArray[];
    HashMap<Integer, Data> hash;
    ArrayList<ArrayList<Integer>> solution;
    int count;
    //ArrayList<Integer> result ;
 
    public void printsolution() {
        System.out.println(solution);
    }
    public Graph(int V) {
 
        this.V = V; //jumlah edge dalam graph
 
        @SuppressWarnings("unchecked")
        // buat listnya
        List<Integer> adjListArray[] = new LinkedList[V];// node sebanyak V
 
        this.adjListArray = adjListArray;

        this.hash = new HashMap<>();
        this.solution = new ArrayList<>();
        //this.result = new ArrayList<>();
        // di dalam adjacentnya, dimasukkin ke linked list baru
        for (int i = 0; i < V; i++) {
            adjListArray[i] = new LinkedList<>();
            
        }
    }
    // Masukkin edgenya
    public void addEdge(Node src, Node dest) {
 
        this.adjListArray[src.getNumber()].add(dest.getNumber());
        
        if (!hash.containsKey(src.getNumber())) {

            Data newData = new Data(src.getData());
            hash.put(src.getNumber(), newData);

        }
        if (!hash.containsKey(dest.getNumber())) {
            Data newData = new Data(dest.getData());
            hash.put(dest.getNumber(), newData);
        }
    }
     
    // Main recursive function to print all possible
    // topological sorts
    private void allTopologicalSortsUtil(boolean[] visited,
                        int[] indegree, ArrayList<Integer> stack) {
        // To indicate whether all topological are found
        // or not
        boolean flag = false;
        // sebanyak jumlah nodenyya, ditambah2
        for (int i = 0; i < this.V; i++) {
           
            // kalau misalnya belum diivisit, dan indegree itu 0 maka
            if (!visited[i] && indegree[i] == 0) {
                // System.out.println(i);
                // visited dari node tersebut dijadikan true
                visited[i] = true;
                // lalu ditambahkan ke dalam stack sudah divisit
                stack.add(i);
                // transversal isi atau adjacend dari list node i tersebut.
                for (Integer adjacent : this.adjListArray[i]) {
                    // yang isinya itu diambil di indegree dikurangi sebanyak 1
                    indegree[adjacent]--;
                    //System.out.println("keluarin hasil indegreenya bentar adjacent "+ adjacent + " "+ indegree[adjacent]);
                }
                // rekurs lagi ...
                allTopologicalSortsUtil(visited, indegree, stack);
                 
                // resetting visited, res and indegree for
                // backtracking saat dilkaukan backtracking visitnya 0
                visited[i] = false;
                stack.remove(stack.size() - 1);
                for (Integer adjacent : this.adjListArray[i]) {
                    indegree[adjacent]++;
                }
                flag = true;
            }
        }
        
        // We reach here if all vertices are visited.
        // So we print the solution here
        if (!flag) {
            // stack.forEach(i -> System.out.print(i + " "));
            // System.out.println();
            solution.add(new ArrayList<>(stack));
        } 
    }
     
    // The function does all Topological Sort.
    // It uses recursive alltopologicalSortUtil() penggunaan dfs untuk melakukan program ini.
    public void allTopologicalSorts() {
        // Mark all the vertices as not visited
        boolean[] visited = new boolean[this.V]; // isi array sebanyak jumlah edges ini.

        // berupa integer of array
        int[] indegree = new int[this.V]; // jumlah array isinyya integer yyang jumlahanyy V, terisi dengan 0
        
        // transversalkan sebanyak jumlah node dalam graf
        for (int i = 0; i < this.V; i++) {
            // di dalam node tersebut isinya tidak 0 maka dilakukan 
            for (Integer var : this.adjListArray[i]) {
                // indegree dari isi list adjacent tersebut bertambah  jika ada yang terkait.
                // misal i = 1, for loop isi dari list node i = 1, ditambah sejumlah anaknya
                indegree[var]++; 
            }
        }

        scoreCalculateVertex(indegree);

        // buatlah stack baru yang bentuknya adah arrayylist
        ArrayList<Integer> stack = new ArrayList<>();
        // lakukan sorting
        allTopologicalSortsUtil(visited, indegree, stack);
    }

    public int recursiveScore(int[] degree, int access) {
        count = 1;
        if (access == degree.length-1) {
            hash.get(access).setScore((10*count));
            return 10;
        }
        else {
            int x = 0;
            for (int adjecent :this.adjListArray[access]) {
                x = recursiveScore(degree, adjecent);
                count++;
                hash.get(access).setScore((x+10)*count);
            }
            return 10 + x;
        }
    }

    // Fungsi ini untuk set score setiap vertex
    public void scoreCalculateVertex(int[] degree) {
        for (int i = 0; i < V; i++){
        for (int adjecent :this.adjListArray[i])  {
            // System.out.println(i+ " degree: " + adjecent);
        }}

        
        ArrayList<Integer> Occured = new ArrayList<>(V);
        for (int i = 0; i < this.V; i++) {
            Occured.add(0);
        }
        for (int i = 0 ;i < this.V; i++) {
            for (int var : this.adjListArray[i]) {
                Occured.set(var, Occured.get(var)+1);
            }
        }
        int indeks = 0;
        for (int nolOccured:Occured) {
            if (nolOccured  == 0) {
                // System.out.println(indeks);
                int temp = recursiveScore(degree, indeks);
                // System.out.println("temp: " + temp);
            }
            indeks++;
        }       
    }

    // Fungsi untuk menentukan langkah efisiensi tertinggi berdasarkan scorenya...
    public void efisiensiLangkah() {
        // untuk print results
        System.out.println("Results: " + solution.size());
        // Tempat untuk hitung kalkulasi 
        ArrayList<Integer> result = new ArrayList<>();
        int next = 0;
        int count = 0;
        // untuk membandingkan score
        int firstHighest = hash.get(solution.get(0).get(0)).getScore();

        // Hasil result diisi dengan 0
        for (int i = 0 ; i < solution.size(); i++) {
            result.add(0);
        }
        // jika score terurut menurun dengan selisih kecil, efisiensi semakin tinggi
        for (ArrayList<Integer> isi : solution) {
            // ambil dari array  untuk bilangan pertama harus merupakan nilai score tertinggi.
            if (hash.get(isi.get(0)).getScore() >= firstHighest) {
                firstHighest = hash.get(isi.get(0)).getScore();
                for (int isian: isi) {
                    int x = hash.get(isian).getScore();
                    if (next > x) {
                        result.set(count, result.get(count)+ (next - x));
                    }
                    next = hash.get(isian).getScore();
                }
            }
            count++;
            next = 0;
        }
        System.out.println("ini"+firstHighest);

        // Tentukan indeks rangking langkah terbaik yaitu yang terkecil
        int resultPerbandingan = 99999;
        for (int i = 1; i < result.size(); i++) {
            if (resultPerbandingan > result.get(i) &&  hash.get(solution.get(i).get(0)).getScore() == firstHighest) {
                resultPerbandingan = result.get(i);
                System.out.println("laksjdslf");
            }
        }
        // Keluarkan hasil solutionnya yang paling efisien
        System.out.println("Hasil Langkah Terbaik");
        int numbering = 1;
        for (int i = 0; i < result.size(); i++ ) {
            if (resultPerbandingan == result.get(i) && hash.get(solution.get(i).get(0)).getScore() == firstHighest) {
                System.out.print(numbering + ". ");
                solution.get(i).forEach(j -> System.out.print(j + " "));
                System.out.println();
                numbering+=1;
            }
        }
    }




    // Print Score masing-masing node yang sudah dikalkulasi dengan algoritma backtracking.
    public void printScore() {
        for (int i = 0; i < V; i++) {
            System.out.println(i + " score: " + hash.get(i).getScore());
        }
    }
     
    // Driver code
    public static void main(String[] args) {
 
        // Create a graph given in the above diagram
 
             
        Graph graph = new Graph(20);
        Node node0 = new Node (0, "Preheat Oven 350F");
        Node node1 = new Node (1, "Hancurkan Graham Crackers");
        Node node2 = new Node (2, "Gabungkan Graham Craker dengan gula dan butter");
        Node node3 = new Node (3, "Hasil ditekan ke panci bawah, tebal sebesar 9 inchi");
        Node node4 = new Node (4, "Prebake selama 8 menit");
        Node node5 = new Node (5, "Crust Terbuat");
        Node node6 = new Node (6, "Tuang batter diatas crust");
        Node node7 = new Node (7, "Bungkin panci kue dengan aluminium foil");
        Node node8 = new Node (8, "Mix cream cheese dan gula pada speed medium-high selama 2 menit");
        Node node9 = new Node (9, "tambahkan sour cream, vannilla, jus lemon");
        Node node10 = new Node (10, "Tambahkan telur satu persatu dalam keceapatn medium.");
        Node node11 = new Node (11, "Buat Batch");
        Node node12 = new Node (12, "Letakkan panci kue ke dalam panci luar kue");
        Node node13 = new Node (13, "Dinginkan cheese cake tanpa air mendidih terbuka");
        Node node14 = new Node (14, "Tuang air mendidih ke dalam panci luar kue");
        Node node15 = new Node (15, "Buat Air Mendidih");
        Node node16 = new Node (16, "Masak Cheesecake 55-70 menit");
        Node node17 = new Node (17, "Matikan oven dan buka sediki selama 1 jam");
        Node node18 = new Node (18, "Dinginkan cheese cake secara terutup dalam kulkas");
        Node node19 = new Node (19,"Cheese Cake");
        
        graph.addEdge(node0, node1);
        graph.addEdge(node1, node2);
        graph.addEdge(node2, node3);
        graph.addEdge(node3, node4);
        graph.addEdge(node4, node5);
        graph.addEdge(node5, node6);
        graph.addEdge(node8, node9);
        graph.addEdge(node9, node10);
        graph.addEdge(node10, node11);
        graph.addEdge(node11, node6);
        graph.addEdge(node7, node6);
        graph.addEdge(node6, node12);
        graph.addEdge(node12, node14);
        graph.addEdge(node15, node14);
        graph.addEdge(node14, node16);
        graph.addEdge(node16, node17);
        graph.addEdge(node17, node13);
        graph.addEdge(node13, node18);
        graph.addEdge(node18, node19);


        System.out.println("All Topological sorts");
        graph.allTopologicalSorts();
        graph.printScore();
        // graph.efisiensiLangkah2();
       graph.efisiensiLangkah();

    }
}
