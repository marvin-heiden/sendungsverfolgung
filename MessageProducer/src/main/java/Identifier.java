import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mifmif.common.regex.Generex;

import java.util.Locale;

public class Identifier {
    private String type;
    private String value;
    private float amount;
    private String currency;

    public Identifier(){
        Generex generator = new Generex("\\b([0-9]{12}|100\\d{31}|\\d{15}|\\d{18}|96\\d{20}|96\\d{32})\\b");
        this.value = generator.random();
        this.amount = Float.parseFloat(String.format(Locale.ROOT,"%.2f",100*Math.random()));
        this.type = "Versandmarke";
        this.currency = "EUR";
    }

    public ObjectNode toJson(){
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode identifierNode = mapper.createObjectNode();

        identifierNode.put("Type", type);
        identifierNode.put("Value", value);
        identifierNode.put("Amount", amount);
        identifierNode.put("Currency", currency);

        return identifierNode;
    }
}
