import React, {useEffect, useState} from 'react';
import {
  ActivityIndicator,
  SafeAreaView,
  StatusBar,
  StyleSheet,
  Text,
  TouchableOpacity,
  View,
} from 'react-native';

import {API} from './src/config/api';

type ServiceState = 'checking' | 'up' | 'down';

function App(): React.JSX.Element {
  const [backend, setBackend] = useState<ServiceState>('checking');
  const [ai, setAi] = useState<ServiceState>('checking');

  const checkServices = async () => {
    setBackend('checking');
    setAi('checking');

    try {
      const res = await fetch(API.health);
      setBackend(res.ok ? 'up' : 'down');
    } catch {
      setBackend('down');
    }

    try {
      const res = await fetch(API.aiHealth);
      setAi(res.ok ? 'up' : 'down');
    } catch {
      setAi('down');
    }
  };

  useEffect(() => {
    checkServices();
  }, []);

  return (
    <SafeAreaView style={styles.safeArea}>
      <StatusBar barStyle="light-content" backgroundColor="#1B5E20" />
      <View style={styles.container}>
        <Text style={styles.title}>AgroVani</Text>
        <Text style={styles.subtitle}>अ‍ॅग्रोवाणी</Text>

        <View style={styles.card}>
          <StatusRow label="Backend (8080)" state={backend} />
          <StatusRow label="AI Service (8000)" state={ai} />
        </View>

        <TouchableOpacity style={styles.button} onPress={checkServices}>
          <Text style={styles.buttonText}>Re-check</Text>
        </TouchableOpacity>
      </View>
    </SafeAreaView>
  );
}

function StatusRow({label, state}: {label: string; state: ServiceState}) {
  return (
    <View style={styles.row}>
      <Text style={styles.rowLabel}>{label}</Text>
      {state === 'checking' ? (
        <ActivityIndicator size="small" color="#1B5E20" />
      ) : (
        <Text style={state === 'up' ? styles.up : styles.down}>
          {state === 'up' ? 'UP' : 'DOWN'}
        </Text>
      )}
    </View>
  );
}

const styles = StyleSheet.create({
  safeArea: {flex: 1, backgroundColor: '#1B5E20'},
  container: {flex: 1, alignItems: 'center', justifyContent: 'center', padding: 24},
  title: {fontSize: 44, fontWeight: '700', color: '#FFFFFF'},
  subtitle: {fontSize: 34, fontWeight: '600', color: '#C8E6C9', marginTop: 8},
  card: {
    backgroundColor: '#FFFFFF',
    borderRadius: 16,
    padding: 20,
    marginTop: 40,
    width: '100%',
  },
  row: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    paddingVertical: 12,
  },
  rowLabel: {fontSize: 18, color: '#212121'},
  up: {fontSize: 18, fontWeight: '700', color: '#2E7D32'},
  down: {fontSize: 18, fontWeight: '700', color: '#C62828'},
  button: {
    backgroundColor: '#FFFFFF',
    borderRadius: 12,
    paddingVertical: 14,
    paddingHorizontal: 40,
    marginTop: 28,
  },
  buttonText: {fontSize: 18, fontWeight: '600', color: '#1B5E20'},
});

export default App;