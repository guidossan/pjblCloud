'use client'

import {PrimeiroComponent, ArrowFunction} from '../components/PrimeiroComponet'
import { useAuth }from '@/resources'
import LoginPage from './login/page'
import GaleriaPage from './galeria/page'

export default function Home() {

  const auth = useAuth();
  if (!auth.getUserSession()){
    return <LoginPage/>
  }
  return (
    <GaleriaPage/>
  )
}
