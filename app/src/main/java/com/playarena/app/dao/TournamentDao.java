package com.playarena.app.dao;

import com.playarena.app.model.Tournament;

public interface TournamentDao {
    public int calculateEstimatedTournamentDuration(Tournament tournament);
}
