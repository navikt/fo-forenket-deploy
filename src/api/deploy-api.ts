import { apiBaseUri } from '../utils/config';
import { Deploy } from '../models/deploy';
import { getEnvironmentByName } from '../utils/environment';
import { fetchToJson } from './utils';

export interface VeraDeploy {
    id: string;
    application: string;
    version: string;
    deployed_timestamp: string;
    environment: string;
}

export function veraDeployToDeploy(veraDeploy: VeraDeploy): Deploy {
    const unixtime = (new Date(veraDeploy.deployed_timestamp)).getTime();
    const timestamp = isNaN(unixtime) ? (new Date()).getTime() : unixtime;

    return {
        id: veraDeploy.id,
        application: veraDeploy.application,
        timestamp,
        version: veraDeploy.version,
        environment: getEnvironmentByName(veraDeploy.environment)
    };
}

export function getAllDeploys(teamId: string): Promise<Deploy[]> {
    const uri = `${apiBaseUri}/deploy?team=${teamId}`;
    return fetchToJson(uri).then((deploys: VeraDeploy[]) => {
            const value = deploys.map(veraDeployToDeploy);
            return Promise.resolve(value);
        });
}
