import java.util.*;
import java.util.concurrent.RecursiveTask;

public class SiteParser extends RecursiveTask<Node>
{
    private final Node node;
    private final Set<String> nodeName;

    public SiteParser(Node node) {
        this.node = node;
        nodeName = Collections.synchronizedSet(new LinkedHashSet<>());
    }

    public SiteParser(Node node, Set<String> nodeName){
        this.nodeName = nodeName;
        this.node = node;
    }

    @Override
    protected Node compute() {
        List<SiteParser> subTask = new LinkedList<>();

        for (String link : node.getLinks()){
            if (nodeName.contains(link)){
                continue;
            }
            nodeName.add(link);
            Node child = new Node(link);
            node.addChild(child);
            SiteParser task = new SiteParser(child, nodeName);
            task.fork();
            subTask.add(task);
        }

        for (SiteParser site : subTask){
            node.getNodes().add(site.join());
        }
        return node;
    }
}
