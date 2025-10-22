package com.david.catalogue.vote;

import jakarta.persistence.Entity;

import java.util.Date;

@Entity
public class Vote {
    private Date startVoting;
    private Date endVoting;
    private int amountOfVotes;

    public Vote(Date startVoting, Date endVoting, int amountOfVotes) {
        this.startVoting = startVoting;
        this.endVoting = endVoting;
        this.amountOfVotes = amountOfVotes;
    }

    public Date getStartVoting() {
        return startVoting;
    }

    public void setStartVoting(Date startVoting) {
        this.startVoting = startVoting;
    }

    public Date getEndVoting() {
        return endVoting;
    }

    public void setEndVoting(Date endVoting) {
        this.endVoting = endVoting;
    }

    public int getAmountOfVotes() {
        return amountOfVotes;
    }

    public void setAmountOfVotes(int amountOfVotes) {
        this.amountOfVotes = amountOfVotes;
    }
}
