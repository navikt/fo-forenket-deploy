import * as React from 'react';
import { Action, Dispatch } from 'redux';
import { connect } from 'react-redux';
import NavFrontendSpinner from 'nav-frontend-spinner';
import { AppState } from '../redux/reducer';
import { getAllTeams } from '../redux/team-duck';
import { velgTeam } from '../redux/team-velger-duck';
import { Team } from '../models/team';
import { getAllDeploys } from '../redux/deploy-duck';
import { getInitialValgtTeam } from '../redux/initial-data-loader-duck';

interface DispatchProps {
    requestInitialData: () => void;
}

interface StateProps {
    isLoadingTeam: boolean;
    isLoadingDeploy: boolean;
}

interface OwnProps {
    spinnerTeam?: boolean;
    spinnerDeploy?: boolean;
    fetchData?: boolean;
}

type DashboardProps = DispatchProps & StateProps & OwnProps;

class Dashboard extends React.Component<DashboardProps> {
    componentDidMount() {
        if(this.props.fetchData === true) {
            this.props.requestInitialData();
        }
    }

    isLoading() {
        const { spinnerTeam, spinnerDeploy, isLoadingDeploy, isLoadingTeam } = this.props;
        return ((spinnerTeam && isLoadingTeam) || (spinnerDeploy && isLoadingDeploy));
    }

    render() {
        if (this.isLoading()) {
            return <NavFrontendSpinner type="L" />;
        }

        return (
            <div>{this.props.children}</div>
        );
    }
}

const mapDispatchToProps = (dispatch: Dispatch<Action>): DispatchProps => ({
    requestInitialData: () => {
        dispatch(getAllTeams())
            .then((teams: Team[]) => {
                const valgtTeamId = getInitialValgtTeam(teams);
                dispatch(velgTeam(valgtTeamId));
                dispatch(getAllDeploys(valgtTeamId));
            });
    }
});

const mapStateToProps = (state: AppState): StateProps => ({
    isLoadingTeam: state.team.loading,
    isLoadingDeploy: state.deploy.loading
});

export default connect(mapStateToProps, mapDispatchToProps)(Dashboard);
