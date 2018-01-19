import {apiBaseUri} from "../utils/config";

export interface Team {
    id: string;
    displayName: string;
}

export function getAllTeams(): Promise<Team[]> {
    const uri = `${apiBaseUri}/teams`;
    return fetch(uri)
        .then((response) => response.json())
}