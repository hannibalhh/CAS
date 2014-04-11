package DSPersistenceManager.Services;

import DSPersistenceManager.Model.Message;
import org.haw.cas.GlobalTypes.MessageInfo.Provenance;
import org.haw.cas.GlobalTypes.Settings.AppSettings;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;


public class MessageDAO extends AbstractDAO{
    private AppSettings appSettings ;

	public MessageDAO(){
		this.dbService = new DatabaseServices();
        this.appSettings = new AppSettings();
	}
	
	public Message getMessageById(String id){
        return this.getById(id,Message.class);
	}

    public void createMessage(Message message){
        this.create(message);
    }
	
	public Iterable<Message> getAllMessages(){
		EntityManager em = dbService.getEntityManager();
		
        TypedQuery<Message> query = em.createNamedQuery(Message.findAll, Message.class);
        List<Message> result = query.getResultList();

        em.close();
        
        return result;
	}

    public Iterable<Message> getMessageChunk(int chunkSize){
        EntityManager em = dbService.getEntityManager();

        TypedQuery<Message> query = em.createNamedQuery(Message.findAll, Message.class).setMaxResults(chunkSize);
        List<Message> result = query.getResultList();
             for(Message message  : result) {
                 delete(message);
            }
        em.close();

        for(Message message :  result){
            if(message.getProvenance() == null){
                message.setProvenance(Provenance.Unknown);
                logger.warn("The provenance of the message: " + message + " was null. It has been set to unknown");
            }
        }
        return result;
    }

    public Iterable<Message> getMessageByAuthor(String author){
        EntityManager em = dbService.getEntityManager();
        TypedQuery<Message> query = em.createNamedQuery(Message.findByAuthor, Message.class);
        query.setParameter("author", author);
        List<Message> result = query.getResultList();
        em.close();
        return result;
    }

    public void update(Message entity) throws IllegalArgumentException{
        checkMessageIsValid(entity);
        super.update(entity);
    }

    public void delete(Message entity){
        super.delete(entity.getId(), Message.class);
    }

    private void checkMessageIsValid(Message message){
        if(message == null || (!(message.isValid()))){
            logger.error("Message: " + message +"is not vaild");
            throw new IllegalArgumentException("No valid Message. Check Attributes for null");
        }
    }
}
