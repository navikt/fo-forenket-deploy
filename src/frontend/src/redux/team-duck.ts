
import * as api from "../api/team-api";

import {Action, Dispatch} from "redux";

enum actionNames {
    LOADING = 'teams/SET_PENDING',
    FETCH_SUCCESS = 'teams/FETCH_SUCCESS',
    FETCH_ERROR = 'teams/FETCH_ERROR'
}

export function getAllTeams() {
    return (dispatch: Dispatch<Action>) => {
        dispatch({ type: actionNames.LOADING });
        api.getAllTeams()
            .then((deploys) => dispatch({ type: actionNames.FETCH_SUCCESS, deploys }));
    };
}