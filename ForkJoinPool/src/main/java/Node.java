import org.jsoup.Jsoup;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

public class Node
{
    private final TreeSet<Node> nodes;
    private final String name;
    private int level;

    public Node(String name){
        this.name = name;
        nodes = new TreeSet<>(Comparator.comparing(o -> o.name));
    }

    public void setLevel(int level){
        this.level = level;
    }

    public void addChild(Node node){
        node.setLevel(level + 1);
        nodes.add(node);
    }

    public  TreeSet<Node> getNodes(){
        return nodes;
    }

    public List<String> getLinks(){
        List<String> list = new ArrayList<>();
        try {
            Thread.sleep(1000);
            Document page = Jsoup.connect(name).get();
            if (page != null){
                Elements urls = page.select("a[href]");
                urls.forEach(u -> {
                    String href = u.attr("abs:href");
                    if (href.matches(name + ".+[/]$") && (!href.equals(name) && !href.contains("#"))){
                        if (href.contains(name)){
                            list.add(href);
                        }
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append(name).append("\n");
        for (Node child : nodes){
            builder.append("\t".repeat(level + 1)).append(child.toString());
        }
        return builder.toString();
    }
}
