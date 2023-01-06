import React, { useState } from 'react'
import { useForm } from "react-hook-form";
import classes from "./Upload.module.css";


const Upload = () => {

    const { register, handleSubmit } = useForm() 
    const [selectedFile, setSelectedFile] = useState();

    const onFileChange = (event) => {
        setSelectedFile(event.currentTarget.files[0]);
    };

    const onSubmit = (data) => {
      console.log(data)
    }

    return (
        <div className={classes.content}>
            {/* <form onSubmit={handleSubmit(onSubmit)}>
                <input ref={register} type="file" name="picture" />
                <button>Submit</button>
            </form> */}

            <input type="file" onChange={onFileChange} />
            {selectedFile && (<p>File Name: {selectedFile.name}</p>)}
        </div>
    )
}

export default Upload