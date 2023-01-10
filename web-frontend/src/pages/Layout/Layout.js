import React, { Fragment } from 'react'
import { Outlet } from 'react-router-dom'
import Header from '../../interface/Header/Header'

const Layout = () => {
  return (
    <Fragment>
        <Header />
        <Outlet />
    </Fragment>
  )
}

export default Layout