import { BrowserRouter, Route, Routes } from 'react-router-dom';
import HomePage from './pages/HomePage';
import WritingDiary from './pages/WritingDiary';
import ActivityRecordPage from './pages/ActivityRecordPage';
import SchedulePage from './pages/SchedulePage';
import BabyMain from './pages/BabyMain';
import Login from './pages/Login';
import GlobalStyle from './styles/GlobalStyle';
import { ModalProvider } from './hooks/useModal';
import Signup from './pages/Signup';
import { Welcome } from './pages/Signup/Welcome';
import { BabyInfoForm } from './components/Signup/BabyInfo/BabyInfoForm';

function App() {
  return (
    <>
      <GlobalStyle />
      <BrowserRouter>
        <ModalProvider>
          <Routes>
            <Route path="/" element={<HomePage />} />
            <Route path="/activity-record" element={<ActivityRecordPage />} />
            <Route path="/baby" element={<BabyMain />} />
            <Route path="/baby/writing" element={<WritingDiary />} />
            <Route path="/login" element={<Login />} />
            <Route path="/signup" element={<Signup />} />
            <Route path="/welcome" element={<Welcome />} />
            <Route path="/schedule" element={<SchedulePage />} />
            <Route path="/babyform" element={<BabyInfoForm />} />
          </Routes>
        </ModalProvider>
      </BrowserRouter>
    </>
  );
}

export default App;
