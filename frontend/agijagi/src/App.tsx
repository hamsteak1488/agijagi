import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Home from './pages/Home';
import WritingDiary from './pages/WritingDiary';
import ActivityRecord from './pages/ActivityRecord';
import SchedulePage from './pages/Schedule';
import GlobalStyle from './styles/GlobalStyle';
import { ModalProvider } from './hooks/useModal';
import BabyMain from './pages/BabyMain';
import Login from './pages/Login';

function App() {
  return (
    <>
      <GlobalStyle />
      <ModalProvider>
        <BrowserRouter>
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/baby/writing" element={<WritingDiary />} />
            <Route path="/activity-record" element={<ActivityRecord />} />
            <Route path="/baby" element={<BabyMain />} />
            <Route path="/login" element={<Login />} />
            <Route path="/schedule" element={<SchedulePage />} />
          </Routes>
        </BrowserRouter>
      </ModalProvider>
    </>
  );
}

export default App;
