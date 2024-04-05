import {BrowserRouter as Router, Route, Routes} from 'react-router-dom'
import HelloWorld from '../components/HelloWorld';
import HelloWorldOnlyClient from '../components/HelloWorldOnlyClient';

const ClientRouter = () => {
    return (
        <div className=''>
            <HelloWorldOnlyClient/>
            <Routes>
                <Route path='/' element={<HelloWorld/>}/>
            </Routes>
        </div>
    );
}

export default ClientRouter;