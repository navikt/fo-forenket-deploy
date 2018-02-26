import * as React from 'react';
import { withRouter, RouteComponentProps } from 'react-router';
import { connect, Dispatch } from 'react-redux';
import { Innholdstittel, Undertittel } from 'nav-frontend-typografi';
import NavFrontendSpinner from 'nav-frontend-spinner';
import ConmmitsForRelease from './commits-for-release';
import { AppState } from '../../redux/reducer';
import { ReleaseWithCommits } from '../../models/release';
import { selectDeploy } from '../../redux/deploy-duck';
import { getEnvironmentByName } from '../../utils/environment';
import { selectReleaseWithCommits, selectIsLoadingRelease } from '../../redux/selectors/release-selectors';
import IssuesTable from '../release-note/kvittering/issues-table';
import { selectIsLoadingIssues } from '../../redux/jira-issue-duck';
import { getInfoForPromote } from '../../redux/promote-duck';
import { TeamAwareLink } from '../team-aware-link';
import { selectValgtTeam } from '../../redux/team-velger-duck';
import { Team } from '../../models/team';
import PromoteJenkinsAnchor from './promote-jenkins-anchor';

interface PromoteRouteProps {
    app: string;
    env: string;
}

interface PromoteStateProps {
    isLoading: boolean;
    release: ReleaseWithCommits;
    fromVersion: string;
    toVersion: string;
    valgtTeam?: Team;
}

interface DispatchProps {
    doGetInfoForPromote: (app: string, fromVersion: string, toVersion: string) => void;
}

type PromoteProps = RouteComponentProps<PromoteRouteProps> & PromoteStateProps & DispatchProps;

class Promote extends React.PureComponent<PromoteProps> {
    componentDidMount() {
        const app = this.props.match.params.app;
        this.props.doGetInfoForPromote(app, this.props.fromVersion, this.props.toVersion);
    }

    render() {
        const props = this.props;
        if (props.isLoading) {
            return <NavFrontendSpinner type="L" />;
        }

        const app = props.release.application;
        const env = props.release.environment.promotesTo ? props.release.environment.promotesTo : '';

        return (
            <section>
                <Innholdstittel className="blokk-m">Promoter {props.match.params.app} til {props.release.environment.promotesTo}</Innholdstittel>
                <Undertittel className="blokk-xs">Endringer fra {props.release.fromVersion} til {props.release.toVersion}</Undertittel>
                <IssuesTable applications={[app]}/>
                <ConmmitsForRelease commits={props.release.commits} />
                <div className="knapperad-promoter">
                    <PromoteJenkinsAnchor application={app} env={env}/>
                    <TeamAwareLink className="knapp" to="/">
                        Avbryt
                    </TeamAwareLink>
                </div>
            </section>
        );
    }
}

function mapStateToProps(state: AppState, ownProps: RouteComponentProps<PromoteRouteProps>): PromoteStateProps {
    const routeParams = ownProps.match.params;
    const environment = getEnvironmentByName(routeParams.env);
    const deployCurrentEnv = selectDeploy(state, routeParams.app, environment.name);
    const deployNextEnv = selectDeploy(state, routeParams.app, environment.promotesTo);

    return {
        isLoading: selectIsLoadingRelease(state) || selectIsLoadingIssues(state),
        release: selectReleaseWithCommits(state, routeParams.app, routeParams.env),
        fromVersion: deployNextEnv ? deployNextEnv.version : '',
        toVersion: deployCurrentEnv ? deployCurrentEnv.version : '',
        valgtTeam: selectValgtTeam(state),
    };
}

function mapDispatchToProps(dispatch: Dispatch<any>): DispatchProps {
    return {
        doGetInfoForPromote: (app, fromVersion, toVersion) => dispatch(getInfoForPromote(app, fromVersion, toVersion))
    };
}

export default withRouter(connect(mapStateToProps, mapDispatchToProps)(Promote));
