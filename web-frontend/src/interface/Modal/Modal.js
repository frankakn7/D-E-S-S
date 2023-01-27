import React, { useState } from "react";
import "./Modal.css";

function Modal() {
  const [modal, setModel] = useState(false);

  const toggleModal = () => {
    setModel(!modal);
  };

  return (
    <>
      {/* if model is true then return model */}
      {modal && (
        <div className="modal">
          <div className="overlay" onClick={toggleModal}></div>
          <div className="modal-content">
            <h2>Error: Invalid Json</h2>
            <button className="close-modal" onClick={toggleModal}>
              Close
            </button>
          </div>
        </div>
      )}
    </>
  );
}

export default Modal;
