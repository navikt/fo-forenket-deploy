import * as React from 'react';
import { Action } from 'redux';
import { connect, Dispatch } from 'react-redux';
import NavFrontendSpinner from 'nav-frontend-spinner';
import { AppState } from '../redux/reducer';
import { getAllDeploys } from '../redux/deploy-duck';
import { getAllTeams } from '../redux/team-duck';

interface DispatchProps {
    requestInitialData: () => void;
}

interface StateProps {
    isLoading: boolean;
}

class Dashboard extends React.Component<DispatchProps & StateProps> {
    componentDidMount() {
        this.props.requestInitialData();
    }

    render() {
        if (this.props.isLoading) {
            return <NavFrontendSpinner />;
        }

        return (
            <div>{this.props.children}</div>
        );
    }
}

const mapDispatchToProps = (dispatch: Dispatch<Action>): DispatchProps => ({
    requestInitialData: () => {
        dispatch(getAllDeploys());
        dispatch(getAllTeams())
    }
});

const mapStateToProps = (state: AppState): StateProps => ({
    isLoading: state.deploy.loading
});

export default connect(mapStateToProps, mapDispatchToProps)(Dashboard);
