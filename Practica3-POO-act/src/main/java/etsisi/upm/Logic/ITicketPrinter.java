package etsisi.upm.Logic;

import etsisi.upm.Model.*;
import java.util.*;


public interface ITicketPrinter<T extends Sales> {

    String print(List<TicketItem<T>> items, Client client);
}