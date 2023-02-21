import React, { Fragment } from "react";
import ErrorModal from "../interface/Modal/ErrorModal/ErrorModal";
import Modal from "../interface/Modal/Modal";

/**
 * Errror Boundary for catching most errors that occur inside the Frontend Application
 */
class ErrorBoundary extends React.Component {
    constructor(props) {
        super(props);
        this.state = { error: false };
    }

    static getDerivedStateFromError(error) {
        // Update state so the next render will show the fallback UI.
        return { error: error };
    }

    componentDidCatch(error, errorInfo) {
        // log the error to an error reporting service
        //console.log(error);
    }

    render() {
        if (this.state.error) {
            //Render Error Modal ontop of gray background displaying the current Error
            return (
                <Fragment>
                    <ErrorModal onClose={() => this.setState({ error: false })}>
                        <pre style={{whiteSpace: 'pre-wrap', textAlign: 'center'}}>{this.state.error.name}</pre>
                        <pre style={{whiteSpace: 'pre-wrap'}}>{this.state.error.message}</pre>
                    </ErrorModal>
                    {/* {this.props.allowChildren && this.props.children} */}
                </Fragment>
            );
        }

        return this.props.children;
    }
}

export default ErrorBoundary;
