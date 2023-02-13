import React, { Fragment } from "react";
import ErrorModal from "../interface/Modal/ErrorModal/ErrorModal";
import Modal from "../interface/Modal/Modal";

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
        // You can also log the error to an error reporting service
        //console.log(error);
    }

    render() {
        if (this.state.error) {
            // You can render any custom fallback UI
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
