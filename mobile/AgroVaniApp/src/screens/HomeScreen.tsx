import React, {useCallback, useEffect, useState} from 'react';
import {
  ActivityIndicator,
  ScrollView,
  StyleSheet,
  Text,
  View,
} from 'react-native';
import {SafeAreaView} from 'react-native-safe-area-context';
import type {NativeStackScreenProps} from '@react-navigation/native-stack';

import BigButton from '../components/BigButton';
import {t} from '../i18n/strings';
import type {RootStackParamList} from '../navigation/types';
import type {Farmer, FarmCrop} from '../services/farmerService';
import {farmerService} from '../services/farmerService';
import {storage} from '../services/storage';
import {colors, fontSize, radius, spacing, touchTarget} from '../theme/theme';

type Props = NativeStackScreenProps<RootStackParamList, 'Home'>;

export default function HomeScreen({navigation}: Props): React.JSX.Element {
  const [farmer, setFarmer] = useState<Farmer | null>(null);
  const [crops, setCrops] = useState<FarmCrop[]>([]);
  const [loading, setLoading] = useState(true);

  const load = useCallback(async () => {
    const stored = await storage.getFarmer();
    setFarmer(stored);

    if (stored) {
      try {
        setCrops(await farmerService.getActiveCropsForFarmer(stored.id));
      } catch {
        setCrops([]);
      }
    }
    setLoading(false);
  }, []);

  useEffect(() => {
    load();
  }, [load]);

  const handleLogout = async () => {
    await storage.clearFarmer();
    navigation.reset({index: 0, routes: [{name: 'Registration'}]});
  };

  if (loading) {
    return (
      <SafeAreaView style={styles.center}>
        <ActivityIndicator size="large" color={colors.primary} />
      </SafeAreaView>
    );
  }

  return (
    <SafeAreaView style={styles.safeArea} edges={['top', 'bottom']}>
      <ScrollView contentContainerStyle={styles.content}>
        <View style={styles.header}>
          <Text style={styles.greeting}>{t.home.greeting},</Text>
          <Text style={styles.name}>{farmer?.fullName ?? ''}</Text>
          {!!farmer?.village && (
            <Text style={styles.village}>
              {farmer.village}
              {farmer.taluka ? `, ${farmer.taluka}` : ''}
            </Text>
          )}
        </View>

        <View style={styles.micWrapper}>
          <BigButton
            label={t.home.askQuestion}
            onPress={() => {}}
            style={styles.mic}
          />
        </View>

        <Text style={styles.sectionTitle}>{t.home.myCrops}</Text>

        {crops.length === 0 ? (
          <Text style={styles.empty}>{t.home.noCrops}</Text>
        ) : (
          crops.map(crop => (
            <View key={crop.id} style={styles.cropCard}>
              <Text style={styles.cropNameMr}>{crop.cropNameMr}</Text>
              <Text style={styles.cropNameEn}>{crop.cropNameEn}</Text>
            </View>
          ))
        )}

        <BigButton
          label={t.home.logout}
          onPress={handleLogout}
          variant="secondary"
          style={styles.logout}
        />
      </ScrollView>
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  safeArea: {
    flex: 1,
    backgroundColor: colors.background,
  },
  center: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
    backgroundColor: colors.background,
  },
  content: {
    padding: spacing.lg,
    paddingBottom: spacing.xxl,
  },
  header: {
    marginBottom: spacing.xl,
  },
  greeting: {
    fontSize: fontSize.body,
    color: colors.textLight,
  },
  name: {
    fontSize: fontSize.title,
    fontWeight: '700',
    color: colors.text,
  },
  village: {
    fontSize: fontSize.body,
    color: colors.textLight,
    marginTop: spacing.xs,
  },
  micWrapper: {
    alignItems: 'center',
    marginBottom: spacing.xl,
  },
  mic: {
    width: '100%',
    minHeight: touchTarget.huge,
  },
  sectionTitle: {
    fontSize: fontSize.large,
    fontWeight: '600',
    color: colors.text,
    marginBottom: spacing.md,
  },
  empty: {
    fontSize: fontSize.body,
    color: colors.textLight,
    paddingVertical: spacing.md,
  },
  cropCard: {
    backgroundColor: colors.surface,
    borderRadius: radius.md,
    padding: spacing.md,
    marginBottom: spacing.sm,
    borderLeftWidth: 6,
    borderLeftColor: colors.primary,
  },
  cropNameMr: {
    fontSize: fontSize.large,
    fontWeight: '700',
    color: colors.text,
  },
  cropNameEn: {
    fontSize: fontSize.small,
    color: colors.textLight,
    marginTop: spacing.xs,
  },
    logout: {
    marginTop: spacing.xl,
  },
});