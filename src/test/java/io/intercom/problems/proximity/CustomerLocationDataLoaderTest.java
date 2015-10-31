package io.intercom.problems.proximity;


import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(URL.class)
public class CustomerLocationDataLoaderTest {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  private CustomerLocationDataLoader sut = new CustomerLocationDataLoader();

  @Test
  public void shouldLoadCustomersFromInputStream() throws Exception {

    String json = "{\"latitude\": \"52.986375\", \"user_id\": 12, \"name\": \"Christina McArdle\", \"longitude\": \"-6.043701\"}";
    List<CustomerLocation> loaded = sut.loadCustomerLocationDetails(new ByteArrayInputStream(json.getBytes()));
    assertEquals(1, loaded.size());
    CustomerLocation customer = loaded.get(0);
    assertEquals(new Double(52.986375), customer.getCoordinates().getLatitude());
    assertEquals(new Double(-6.043701), customer.getCoordinates().getLongitude());
    assertEquals(new Integer(12), customer.getUserId());
    assertEquals("Christina McArdle", customer.getName());

  }

  @Test(expected = AssertionError.class)
  public void shouldFailValidationIfNameMissing() throws Exception {
    new CustomerLocation(1, null, 123.123, 123.123).validate();
  }

  @Test(expected = AssertionError.class)
  public void shouldFailValidationIfUserIdMissing() throws Exception {
    new CustomerLocation(null, "Bob", 123.123, 123.123).validate();
  }

  @Test(expected = AssertionError.class)
  public void shouldFailValidationIfLatiudeMissing() throws Exception {
    new CustomerLocation(1, "bob", null, 123.123).validate();
  }

  @Test(expected = AssertionError.class)
  public void shouldFailValidationIfLongitudeMissing() throws Exception {
    new CustomerLocation(1, "bob", 123.123, null).validate();
  }

  @Test
  public void shouldHaveAccessToCoordinates(){
    CustomerLocation loc =  new CustomerLocation(1, "bob", 123.123, 123.123);
    loc.validate();
    assertEquals(new Integer(1), loc.getUserId());
    assertEquals("bob", loc.getName());
    assertEquals(new Double(123.123), loc.getLatitude());
    assertEquals(new Double(123.123), loc.getLongitude());
  }

  @Test
  public void shouldIgnoreMalformedJson() throws Exception {
    String malformed = "not json";
    List<CustomerLocation> loaded = sut.loadCustomerLocationDetails(new ByteArrayInputStream(malformed.getBytes()));
    assertEquals(0, loaded.size());

  }

  @Test
  public void shouldLoadCustomersFromUrl() throws Exception {
    CustomerLocationDataLoader sut = new CustomerLocationDataLoader();
    List<CustomerLocation> loaded = sut.loadCustomerLocationDetails(new URL("https://gist.githubusercontent.com/brianw/19896c50afa89ad4dec3/raw/6c11047887a03483c50017c1d451667fd62a53ca/gistfile1.txt"));
    assertEquals(32, loaded.size());
  }

  @Test(expected = IOException.class)
  public void shouldFailIfUrlInaccessible() throws Exception {
    new CustomerLocationDataLoader().loadCustomerLocationDetails(new URL("http://192.168.1.123/deosnotexist"));
  }

  @Test(expected = UncheckedIOException.class)
  public void shouldFailIfErrorReadingStreamContents() throws Exception {
    InputStream mockStream = mock(InputStream.class);
    when(mockStream.read()).thenThrow(new IOException("Something barfed"));
    new CustomerLocationDataLoader().loadCustomerLocationDetails(mockStream);
  }

  @Test(expected = UncheckedIOException.class)
  public void shouldFailIfErrorReadingURLContents() throws Exception {
    InputStream mockStream = mock(InputStream.class);
    when(mockStream.read()).thenThrow(new IOException("Something barfed"));

    URLConnection mockConnection = mock(URLConnection.class);
    when(mockConnection.getInputStream()).thenReturn(mockStream);

    URL mockUrl = mock(URL.class);
    when(mockUrl.openConnection()).thenReturn(mockConnection);
    new CustomerLocationDataLoader().loadCustomerLocationDetails(mockStream);
  }


  @Test
  public void shouldNotChokeIfInputStreamIsEmpty() throws Exception {
    List<CustomerLocation> loaded = new CustomerLocationDataLoader().loadCustomerLocationDetails((InputStream) null);
    assertEquals(0, loaded.size());

    loaded = new CustomerLocationDataLoader().loadCustomerLocationDetails(new ByteArrayInputStream(new byte[0]));
    assertEquals(0, loaded.size());
  }
}