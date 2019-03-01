/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

/**
 *
 * @author vitorpeixoto
 */
public interface Evento {
        
    public void notifyUsers();
    public void addUser(User u);
    public void removeUser(User u);
    
}
