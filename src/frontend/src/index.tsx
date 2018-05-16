import * as React from 'react';
import * as ReactDOM from 'react-dom';
import App from './app';
import 'whatwg-fetch';

ReactDOM.render(
    <App/>,
    document.getElementById('root') as HTMLElement
);
