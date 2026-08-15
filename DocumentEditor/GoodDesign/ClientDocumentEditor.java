package DocumentEditor.GoodDesign;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
// DocumentEditor class managing client interactions
class DocumentEditor{
    private Document doc;
    private Persistence db;
    private String documentReader ="";

    public DocumentEditor(Document doc,Persistence db){
        this.doc=doc;
        this.db=db;
    }

    public void addText(String text){
        doc.addElement(new TextElement(text));
    }
    public void addImage(String image){
        doc.addElement(new ImageElement(image));
    }
    public void addnewLineElement(){
        doc.addElement(new NewLineElement());
    }
    public void addTabSpace(){
        doc.addElement(new TabSpaceElement());
    }
    public String renderDocument(){
        if(documentReader.isEmpty())
        documentReader =  doc.render();
    return documentReader;
    }
    public void save(){
        db.save(renderDocument());
    }
}
// Document class responsible for holding a collection of elements
class Document{
    private List<DocumentElement> elements = new ArrayList<>();
    public void addElement(DocumentElement element){
        elements.add(element);

    }
    // Renders the document by concatenating the render output of all elements.
    public String render(){
        StringBuilder result = new StringBuilder();
        for(DocumentElement element:elements){
            result.append(element.renderElement());
        }
        return result.toString();
    }


}
//Interface for document elements
interface DocumentElement{
    String renderElement();
}
// Concrete implementation for text elements
class TextElement implements DocumentElement{
    private String text;
    public TextElement(String text){
        this.text=text;
    }
    public String renderElement(){
        return text;


    }
}
// Concrete implementation for image elements
class ImageElement implements DocumentElement{
    private String path;
    public ImageElement(String path){
        this.path=path;
    }
    public String renderElement(){
        return "[Image: " + path + "]";

    }
}
// NewLineElement represents a line break in the document.
class NewLineElement implements DocumentElement {
    @Override
    public String renderElement() {
        return "\n";
    }
}

// TabSpaceElement represents a tab space in the document.
class TabSpaceElement implements DocumentElement {
    @Override
    public String renderElement() {
        return "\t";
    }
}
// Persistence Interface
interface Persistence{
    void save(String dataString);
}
// FileStorage implementation of Persistence
class SaveToFile implements Persistence{
    public void save(String data) {
        try {
            FileWriter outFile = new FileWriter("document.txt");
            outFile.write(data);
            outFile.close();
            System.out.println("Document saved to document.txt");
        } catch (IOException e) {
            System.out.println("Error: Unable to open file for writing.");
        }
    }
}
// Placeholder DBStorage implementation
class SaveToDB implements Persistence{
    public void save(String data){
        //DB logic
        System.out.println("Save to DB Storage");
    }
}
// Client usage example
public class ClientDocumentEditor {
    public static void main(String[] args) {
        Document doc = new Document();
        Persistence db = new SaveToFile();

        DocumentEditor editor = new DocumentEditor(doc,db);
        // Simulate a client using the editor with common text formatting features.
        editor.addText("Hello, world!");
        editor.addnewLineElement();
        editor.addText("This is a real-world document editor example.");
        editor.addnewLineElement();
        editor.addTabSpace();
        editor.addText("Indented text after a tab space.");
        editor.addnewLineElement();
        editor.addImage("picture.jpg");
         // Render and display the final document.
        System.out.println(editor.renderDocument());

        editor.save();
    }

}
